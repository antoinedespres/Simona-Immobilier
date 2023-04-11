package com.simona.auth.web.controller;

import com.simona.auth.dto.AuthResponse;
import com.simona.auth.model.Account;
import com.simona.auth.dto.AuthRequest;
import com.simona.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final AuthService authService;

    AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody Account account) {

        try {
            Account savedAccount = authService.saveAccount(account);
            AuthResponse response = new AuthResponse();
            response.setEmail(savedAccount.getEmail());
            response.setLastName(savedAccount.getLastName());
            response.setFirstName(savedAccount.getFirstName());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        if (authenticate.isAuthenticated()) {
            return authService.generateToken(authRequest.getEmail());
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam String token) {
        try {
            if (!authService.isValidToken(token)) return ResponseEntity.ok("Token is invalid");
            return ResponseEntity.ok().body("Token is valid");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Token is invalid");
        }
    }

}
