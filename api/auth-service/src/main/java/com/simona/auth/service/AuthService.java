package com.simona.auth.service;

import com.simona.auth.model.Account;
import com.simona.auth.util.JwtUtil;
import com.simona.auth.web.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class AuthService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    AuthService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Account saveAccount(Account credential) {
        if (accountRepository.findByEmail(credential.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        return accountRepository.save(credential);
    }

    public String generateToken(String username) {
        return jwtUtil.generateToken(username);
    }

    public boolean isValidToken(String token) {
        return jwtUtil.isValidToken(token);
    }
}
