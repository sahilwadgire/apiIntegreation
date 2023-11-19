package com.authentication.service;

import com.authentication.entity.Token;
import com.authentication.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService
{
    @Autowired
    TokenRepository tokenRepository;

    public Token addToDB(Token token)
    {
        tokenRepository.save(token);
        return token;
    }
    public List<Token> findAll()
    {
        return tokenRepository.findAll();
    }
}
