package com.laptopshopResful.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.laptopshopResful.domain.entity.User;
import com.laptopshopResful.domain.request.RequestLoginDTO;
import com.laptopshopResful.domain.response.ResLoginDTO;
import com.laptopshopResful.service.UserService;
import com.laptopshopResful.utils.SecurityUtils;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

@RestController
public class AuthController {
    private final SecurityUtils securityUtils;
    private final AuthenticationManagerBuilder builder;
    private final UserService userService;
    @Value("${djnd.jwt.access-token-validity-in-seconds}")
    private Long refreshTokenExpiration;

    public AuthController(AuthenticationManagerBuilder builder, SecurityUtils securityUtils, UserService userService) {
        this.builder = builder;
        this.userService = userService;
        this.securityUtils = securityUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<ResLoginDTO> login(@Valid @RequestBody RequestLoginDTO dto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                dto.getUsername(), dto.getPassword());
        Authentication authentication = builder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ResLoginDTO res = new ResLoginDTO();
        User user = this.userService.handleGetUserByUsername(dto.getUsername());
        if (user != null) {
            ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin(user.getId(), user.getEmail(), user.getName());
            res.setUser(userLogin);
        }
        // create token
        String accessToken = this.securityUtils.createAccessToken(authentication.getName(), res);
        res.setAccessToken(accessToken);

        // create refresh token
        String refreshToken = this.securityUtils.createRefreshToken(dto.getUsername(), res);
        // update
        this.userService.updateUserToken(refreshToken, dto.getUsername());
        // set cookies
        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(res);
    }
}
