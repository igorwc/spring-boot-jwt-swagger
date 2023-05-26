package com.cavalcanti.service.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cavalcanti.domain.Token;
import com.cavalcanti.repository.TokenRepository;

@Service
public class TokenService    {
    
    @Autowired
    private TokenRepository tokenRepository;
    
    public Optional<Token> findByToken(String token) {
	return tokenRepository.findByToken(token);
    }

    public void save(Token storedToken) {
	tokenRepository.save(storedToken);
	
    }
}
