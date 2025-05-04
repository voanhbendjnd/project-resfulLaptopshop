package com.laptopshopResful.controller.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import com.laptopshopResful.domain.entity.User;
import com.laptopshopResful.domain.request.RequestLoginDTO;
import com.laptopshopResful.domain.request.RequestOTP;
import com.laptopshopResful.domain.response.ResLoginDTO;
import com.laptopshopResful.domain.response.user.ResCreateUserDTO;
import com.laptopshopResful.service.CartService;
import com.laptopshopResful.service.EmailService;
import com.laptopshopResful.service.OtpService;
import com.laptopshopResful.service.UserService;
import com.laptopshopResful.utils.SecurityUtils;
import com.laptopshopResful.utils.annotation.ApiMessage;
import com.laptopshopResful.utils.error.IdInvalidException;

@RestController
public class AuthController {

    private final SecurityUtils securityUtils;
    private final AuthenticationManagerBuilder builder;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final EmailService emailService;
    @Value("${djnd.jwt.access-token-validity-in-seconds}")
    private Long refreshTokenExpiration;

    public AuthController(AuthenticationManagerBuilder builder, SecurityUtils securityUtils,
            UserService userService, PasswordEncoder passwordEncoder, CartService cartService,
            OtpService otpService,
            EmailService emailService) {
        this.builder = builder;
        this.securityUtils = securityUtils;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.otpService = otpService;
        this.emailService = emailService;

    }

    @PostMapping("/auth/login")
    @ApiMessage("Login")
    public ResponseEntity<ResLoginDTO> login(@Valid @RequestBody RequestLoginDTO dto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                dto.getUsername(), dto.getPassword());
        Authentication authentication = builder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ResLoginDTO res = new ResLoginDTO();
        User user = this.userService.handleGetUserByUsername(dto.getUsername());
        if (user != null) {
            ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin(user.getId(), user.getEmail(), user.getName(),
                    user.getRole());
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

    @GetMapping("/auth/account")
    @ApiMessage("Fetch Account")
    public ResponseEntity<ResLoginDTO.UserGetAccount> getAccount() {
        String email = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        User user = this.userService.handleGetUserByUsername(email);
        ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin();
        ResLoginDTO.UserGetAccount userGetAccount = new ResLoginDTO.UserGetAccount();
        if (user != null) {
            userLogin.setId(user.getId());
            userLogin.setEmail(user.getEmail());
            userLogin.setName(user.getName());
            userLogin.setRole(user.getRole());
            userGetAccount.setUser(userLogin);
        }
        return ResponseEntity.ok(userGetAccount);
    }

    // auto refresh token for user if use app or web...
    @GetMapping("/auth/refresh")
    @ApiMessage("Get new token refresh")
    public ResponseEntity<ResLoginDTO> getRefreshToken(
            @CookieValue(name = "refresh_token", defaultValue = "abc") String refreshToken) throws IdInvalidException {
        if (refreshToken.equals("abc")) {
            throw new IdInvalidException(">>> Not trasmit<<<");
        }
        Jwt decodedToken = this.securityUtils.checkValidRefreshToken(refreshToken);
        String email = decodedToken.getSubject();
        User user = this.userService.findByRefreshTokenAndEmail(refreshToken, email);
        if (user == null) {
            throw new IdInvalidException(">>> Refresh token not vaild");
        }
        ResLoginDTO res = new ResLoginDTO();
        User user2 = this.userService.handleGetUserByUsername(email);
        if (user2 != null) {
            ResLoginDTO.UserLogin aluka = new ResLoginDTO.UserLogin(user2.getId(), user2.getEmail(), user2.getName(),
                    user2.getRole());
            res.setUser(aluka);
        }
        // create token
        String access_token = this.securityUtils.createAccessToken(email, res);
        res.setAccessToken(access_token);
        // create token re
        String new_refreshToken = this.securityUtils.createRefreshToken(email, res);
        // update
        this.userService.updateUserToken(new_refreshToken, email);
        // set cookies
        ResponseCookie cookie = ResponseCookie.from("refresh_token", new_refreshToken)
                .httpOnly(true) // chi chap nhan moi server nay
                .secure(true) // chi nhan https
                .path("/") // cho phep gui cookies cho tat ca giao thuc
                .maxAge(refreshTokenExpiration) // thoi gian het han

                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(res);
    }

    // if user logout, refresh token will deleted
    @PutMapping("auth/logout")
    @ApiMessage("Logout and delete token")
    public ResponseEntity<Void> logoutAndDeleteRefreshToken(
            @CookieValue(name = "refresh_token", defaultValue = "djnd") String tokan) throws IdInvalidException {
        Jwt decodedToken = this.securityUtils.checkValidRefreshToken(tokan);
        String email = decodedToken.getSubject();
        User user = this.userService.findByRefreshTokenAndEmail(tokan, email);
        user.setRefreshToken(null);
        this.userService.update(user);
        return ResponseEntity.ok(null);
    }

    // same ob
    @PostMapping("auth/logout")
    @ApiMessage("Logout and delete token")
    public ResponseEntity<Void> postLogout2() throws IdInvalidException {
        String email = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        if (email.equals("")) {
            throw new IdInvalidException("Access Token không hợp lệ");
        }
        // update
        this.userService.updateUserToken(null, email);

        // remove
        ResponseCookie cookie = ResponseCookie
                .from("refresh_token", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(null);
    }

    // for register
    @PostMapping("/auth/register")
    @ApiMessage("Create account for register")
    public ResponseEntity<ResCreateUserDTO> createUser(@Valid @RequestBody User user) throws IdInvalidException {
        if (this.userService.existsByEmail(user.getEmail())) {
            throw new IdInvalidException("Email already exists");
        }
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);

        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.create(user));
    }

    @PostMapping("/auth/forget-password")
    @ApiMessage("Get OTP")
    public ResponseEntity<Void> getOTP(@RequestBody RequestLoginDTO request) throws IdInvalidException {
        if (this.userService.existsByEmail(request.getUsername())) {
            this.emailService.sendOtp(this.otpService.getCodeOtp(request.getUsername()), request.getUsername());
            return ResponseEntity.ok(null);
        }
        throw new IdInvalidException("Email not exist!");
    }

    @PostMapping("/auth/input-otp")
    @ApiMessage("Input OTP and return accesstoken")
    public ResponseEntity<String> inputOTP(@RequestBody RequestOTP re) {
        return ResponseEntity.ok(this.otpService.getAccessTokenByReFresh(re.getCode(), re.getEmail()));
    }

    @PostMapping("/auth/change-password")
    @ApiMessage("Change password")
    public ResponseEntity<String> changePassword(@RequestBody RequestLoginDTO re) {
        String email = SecurityUtils.getCurrentUserLogin().get();
        User user = this.userService.getUserByEmail(email);
        String hashPassword = this.passwordEncoder.encode(re.getPassword());
        user.setPassword(hashPassword);
        this.userService.saveUser(user);
        return ResponseEntity.ok("Change password successful!");
    }
}
