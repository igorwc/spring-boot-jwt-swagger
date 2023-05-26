package com.cavalcanti.controller.auth;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cavalcanti.dto.auth.AuthRequestDTO;
import com.cavalcanti.dto.auth.AuthResponseDTO;
import com.cavalcanti.dto.user.RegisterUserDTO;
import com.cavalcanti.service.auth.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
	    @RequestBody RegisterUserDTO request) {
	return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AuthResponseDTO> authenticate(
	    @RequestBody AuthRequestDTO request) {

	System.out.println("TEntando logare");
	return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
	    HttpServletRequest request,
	    HttpServletResponse response) throws IOException {
	service.refreshToken(request, response);
    }

}
