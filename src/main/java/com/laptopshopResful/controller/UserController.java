package com.laptopshopResful.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.laptopshopResful.domain.entity.User;
import com.laptopshopResful.domain.response.user.ResCreateUserDTO;
import com.laptopshopResful.domain.response.user.ResUpdateUserDTO;
import com.laptopshopResful.service.UserService;
import com.laptopshopResful.utils.annotation.ApiMessage;
import com.laptopshopResful.utils.error.IdInvalidException;

import jakarta.validation.Valid;

@RestController
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public UserController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/users")
    @ApiMessage("Create a new User")
    public ResponseEntity<ResCreateUserDTO> create(@Valid @RequestBody User user) throws IdInvalidException {
        if (this.userService.existsByEmail(user.getEmail())) {
            throw new IdInvalidException("Email has exists in the system!");
        }
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        return ResponseEntity.ok(this.userService.create(user));
    }

    @PutMapping("/users")
    public ResponseEntity<ResUpdateUserDTO> update(@Valid @RequestBody User user)
            throws IdInvalidException {
        if (!this.userService.existsById(user.getId())) {
            throw new IdInvalidException("Id with " + user.getId() + " not exists in the system");
        }
        return ResponseEntity.ok(this.userService.update(user));
    }
}
