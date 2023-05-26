package com.cavalcanti.service.auth;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cavalcanti.domain.Token;
import com.cavalcanti.domain.User;
import com.cavalcanti.domain.enums.TokenType;
import com.cavalcanti.dto.auth.AuthRequestDTO;
import com.cavalcanti.dto.auth.AuthResponseDTO;
import com.cavalcanti.dto.user.RegisterUserDTO;
import com.cavalcanti.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
//  private final UserRepository repository;
  private final UserService userService;
//  private final TokenRepository tokenRepository;
  private final TokenService tokenService;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public PasswordEncoder getPasswordEncoder() {
      return passwordEncoder;
  }
  public AuthResponseDTO register(RegisterUserDTO newUser) {
    var user = RegisterUserDTO.builder()
        .firstname(newUser.getFirstname())
        .lastname(newUser.getLastname())
        .email(newUser.getEmail())
        .password(passwordEncoder.encode(newUser.getPassword()))
        .role(newUser.getRole())
        .build();
    var savedUser = userService.createUserModel(user);
    var jwtToken = jwtService.generateToken(RegisterUserDTO.toModel(newUser));
    var refreshToken = jwtService.generateRefreshToken(RegisterUserDTO.toModel(newUser));
    saveUserToken(savedUser, jwtToken);
    return AuthResponseDTO.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
  }

  public AuthResponseDTO authenticate(AuthRequestDTO authDTO) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authDTO.getEmail(),
            authDTO.getPassword()
        )
    );
    var user = userService.findByEmail(authDTO.getEmail());
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens((User) user);
    saveUserToken((User) user, jwtToken);
    return AuthResponseDTO.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenService.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenService.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenService.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.userService.findByEmail(userEmail);
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens((User) user);
        saveUserToken((User) user, accessToken);
        var authResponse = AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}

