package com.laptopshopResful.controller.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.laptopshopResful.domain.entity.CartDetail;
import com.laptopshopResful.domain.entity.Product;
import com.laptopshopResful.domain.request.RequestAddToCart;
import com.laptopshopResful.domain.response.cart.ResCartDTO;
import com.laptopshopResful.repository.CartDetailRepository;
import com.laptopshopResful.service.CartService;
import com.laptopshopResful.service.ProductService;
import com.laptopshopResful.utils.annotation.ApiMessage;
import com.laptopshopResful.utils.error.IdInvalidException;

import jakarta.validation.Valid;

@RestController
public class CartDetailConcoller {
    private final CartService cartService;
    private final ProductService productService;

    public CartDetailConcoller(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @PostMapping("/client/add-to-cart/{id}")
    @ApiMessage("Add product to cart")
    public ResponseEntity<String> addToCart(@PathVariable("id") Long id, @Valid @RequestBody RequestAddToCart request)
            throws IdInvalidException {
        Product product = productService.fetch(id);
        if (product.getQuantity() < request.getQuantity()) {
            throw new IdInvalidException("Quantity product not enough for add to cart!");
        }
        this.cartService.addToCart(id, request.getQuantity());

        return ResponseEntity.ok("Add to cart successful!");
    }

    @GetMapping("/client/cart")
    @ApiMessage("Open a cart")
    public ResponseEntity<ResCartDTO> getCart() {
        return ResponseEntity.ok(this.cartService.getCartDetail());
    }

    @PatchMapping("/carts/{id}")
    @ApiMessage("Increase or Decrease quanity product")
    public void setInDe(@PathVariable("id") Long id, @Valid @RequestBody RequestAddToCart request)
            throws IdInvalidException {
        this.cartService.setIncreaseOrDecreaseForProduct(id, request.getOperation(), request.getQuantity());
    }

}
