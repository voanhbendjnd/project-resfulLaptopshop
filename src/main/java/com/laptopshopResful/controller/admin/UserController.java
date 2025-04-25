package com.laptopshopResful.controller.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.laptopshopResful.domain.entity.User;
import com.laptopshopResful.domain.response.ResultPaginationDTO;
import com.laptopshopResful.domain.response.user.ResCreateUserDTO;
import com.laptopshopResful.domain.response.user.ResFetchUserDTO;
import com.laptopshopResful.domain.response.user.ResUpdateUserDTO;
import com.laptopshopResful.service.UserService;
import com.laptopshopResful.utils.annotation.ApiMessage;
import com.laptopshopResful.utils.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

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
    @ApiMessage("Update user")
    public ResponseEntity<ResUpdateUserDTO> update(@Valid @RequestBody User user)
            throws IdInvalidException {
        if (!this.userService.existsById(user.getId())) {
            throw new IdInvalidException("Id with " + user.getId() + " not exists in the system!");
        }
        return ResponseEntity.ok(this.userService.update(user));
    }

    @GetMapping("/users/{id}")
    @ApiMessage("Fetch user by id")
    public ResponseEntity<ResFetchUserDTO> fetch(@PathVariable("id") Long id) throws IdInvalidException {
        if (this.userService.existsById(id)) {
            return ResponseEntity.ok(this.userService.fecth(id));
        }
        throw new IdInvalidException(String.format("Id with %d not exists in the system!", id));
    }

    @DeleteMapping("/users/{id}")
    @ApiMessage("Delete user by id")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) throws IdInvalidException {
        if (this.userService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        throw new IdInvalidException(String.format("Id with %d not exists in the system", id));
    }

    @GetMapping("/users")
    @ApiMessage("Fetch all user")
    public ResponseEntity<ResultPaginationDTO> fetchAll(@Filter Specification<User> spec, Pageable pageable)
            throws IdInvalidException {
        return ResponseEntity.ok(this.userService.fetchAllWithPagination(pageable, spec));
    }
}
