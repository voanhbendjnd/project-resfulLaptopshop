package com.laptopshopResful.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laptopshopResful.domain.entity.Otp;
import com.laptopshopResful.domain.entity.User;
import com.laptopshopResful.domain.response.ResLoginDTO;
import com.laptopshopResful.repository.OptRepository;
import com.laptopshopResful.utils.SecurityUtils;

@Service
public class OtpService {
    private final OptRepository optRepository;
    private final UserService userService;
    private final AuthenticationManagerBuilder builder;
    private final SecurityUtils securityUtils;

    // private final SecurityUtils securityUtils;

    public OtpService(OptRepository optRepository, UserService userService, AuthenticationManagerBuilder builder,
            SecurityUtils securityUtils) {
        this.optRepository = optRepository;
        this.userService = userService;
        this.builder = builder;
        this.securityUtils = securityUtils;

    }

    public Integer getCodeOtp(String email) {
        User user = this.userService.getUserByEmail(email);
        Random random = new Random();
        if (this.userService.existsByEmail(email) && user.getOtp() == null) {

            int currentdb = random.nextInt(1000, 9999);
            Otp otp = new Otp();
            otp.setOptCode(currentdb * 1L);
            otp.setUser(user);
            this.optRepository.save(otp);
            return currentdb;
        } else if (user.getOtp() != null) {
            Otp currentOtp = user.getOtp();
            int currentdb = random.nextInt(1000, 9999);
            currentOtp.setOptCode(currentdb * 1L);
            this.optRepository.save(currentOtp);
            return currentdb;
        }
        return null;

    }

    @Value("${djnd.jwt.access-token-validity-in-seconds}")
    private Long refreshTokenExpiration;

    @Transactional
    public String inputOtp(Long otpCode, String email) {

        User user = this.userService.getUserByEmail(email);

        if (user.getOtp().getOptCode().equals(otpCode)) {
            this.optRepository.delete(user.getOtp());

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,
                    "123456");
            Authentication authentication = builder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            ResLoginDTO res = new ResLoginDTO();
            if (user != null) {
                ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin(user.getId(),
                        user.getEmail(), user.getName(),
                        user.getRole());
                res.setUser(userLogin);
            }
            // create token
            String accessToken = this.securityUtils.createAccessToken(authentication.getName(), res);
            res.setAccessToken(accessToken);
            // create refresh token
            String refreshToken = this.securityUtils.createRefreshToken(email, res);
            // update
            this.userService.updateUserToken(refreshToken, email);
            // set cookies
            ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(refreshTokenExpiration)
                    .build();
            return res.getAccessToken();
        } else {
            return "OTP not exactly!";
        }

    }

    @Transactional
    public String getAccessTokenByReFresh(Long otpCode, String email) {
        User user = this.userService.getUserByEmail(email);
        if (user.getOtp().getOptCode().equals(otpCode)) {
            this.optRepository.delete(user.getOtp());
            Jwt decoded = this.securityUtils.checkValidRefreshToken(user.getRefreshToken());
            String emailNeed = decoded.getSubject();
            User userNeed = this.userService.findByRefreshTokenAndEmail(user.getRefreshToken(), emailNeed);
            if (userNeed == null) {
                return "Error in processing";
            }
            ResLoginDTO res = new ResLoginDTO();
            User userNew = this.userService.handleGetUserByUsername(emailNeed);
            if (userNew != null) {
                ResLoginDTO.UserLogin auluka = new ResLoginDTO.UserLogin(userNew.getId(), userNew.getEmail(),
                        userNew.getName(), userNew.getRole());
                res.setUser(auluka);
            }
            String accessToken = this.securityUtils.createAccessToken(emailNeed, res);

            res.setAccessToken(accessToken);
            String newReFreshToken = this.securityUtils.createRefreshToken(emailNeed, res);
            this.userService.updateUserToken(newReFreshToken, emailNeed);
            // ResponseCookie cookie = ResponseCookie.from("refresh_token", newReFreshToken)
            // .httpOnly(true)
            // .secure(true)
            // .path("/")
            // .maxAge(refreshTokenExpiration)
            // .build();
            return accessToken;
        }
        return ">>> OTP invalid <<<";

    }

}
