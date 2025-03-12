package com.laptopshopResful.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.laptopshopResful.domain.request.RequestLoginDTO;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

@RestController
public class AuthController {
    private final AuthenticationManagerBuilder builder;

    public AuthController(AuthenticationManagerBuilder builder) {
        this.builder = builder;
    }

    @PostMapping("/login")
    public ResponseEntity<RequestLoginDTO> login(@Valid @RequestBody RequestLoginDTO dto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                dto.getUsername(), dto.getPassword());
        Authentication authentication = builder.getObject().authenticate(authenticationToken); // compare between user
                                                                                               // and password
        return ResponseEntity.ok().body(dto);
    }
}
