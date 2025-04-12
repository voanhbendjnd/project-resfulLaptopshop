package com.laptopshopResful.controller.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.laptopshopResful.domain.entity.CartDetail;
import com.laptopshopResful.domain.request.RequestAddToCart;
import com.laptopshopResful.repository.CartDetailRepository;
import com.laptopshopResful.service.CartService;
import com.laptopshopResful.utils.annotation.ApiMessage;
import com.laptopshopResful.utils.error.IdInvalidException;

import jakarta.validation.Valid;

@RestController
public class CartDetailConcoller {
    private final CartService cartService;

    public CartDetailConcoller(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/client/add-to-cart/{id}")
    @ApiMessage("Add product to cart")
    public ResponseEntity<String> addToCart(@PathVariable("id") Long id, @Valid @RequestBody RequestAddToCart request)
            throws IdInvalidException {
        this.cartService.addToCart(id, request.getQuantity());
        return ResponseEntity.ok("Add to cart successful!");
    }
}
