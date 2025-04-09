package com.laptopshopResful.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.laptopshopResful.domain.entity.Cart;
import com.laptopshopResful.service.CartService;
import com.laptopshopResful.service.UserService;
import com.laptopshopResful.utils.annotation.ApiMessage;
import com.laptopshopResful.utils.error.IdInvalidException;

@RestController
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @PostMapping("/carts")
    @ApiMessage("Create a new Cart")
    public ResponseEntity<Cart> create(@RequestBody Cart cart) throws IdInvalidException {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.cartService.create(cart));
    }
}
