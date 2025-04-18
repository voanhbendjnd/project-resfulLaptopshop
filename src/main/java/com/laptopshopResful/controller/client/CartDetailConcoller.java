package com.laptopshopResful.controller.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.laptopshopResful.domain.entity.Cart;
import com.laptopshopResful.domain.entity.CartDetail;
import com.laptopshopResful.domain.entity.Product;
import com.laptopshopResful.domain.entity.User;
import com.laptopshopResful.domain.request.RequestAddToCart;
import com.laptopshopResful.domain.request.RequestCheckoutCart;
import com.laptopshopResful.domain.response.cart.ResCartCheckoutDTO;
import com.laptopshopResful.domain.response.cart.ResCartDTO;
import com.laptopshopResful.repository.CartDetailRepository;
import com.laptopshopResful.repository.UserRepository;
import com.laptopshopResful.service.CartService;
import com.laptopshopResful.service.ProductService;
import com.laptopshopResful.service.UserService;
import com.laptopshopResful.utils.SecurityUtils;
import com.laptopshopResful.utils.annotation.ApiMessage;
import com.laptopshopResful.utils.error.IdInvalidException;

import jakarta.validation.Valid;

@RestController
public class CartDetailConcoller {
    private final CartService cartService;
    private final ProductService productService;
    private final SecurityUtils securityUtils;
    private final UserService userService;

    public CartDetailConcoller(CartService cartService, ProductService productService, SecurityUtils securityUtils,
            UserService userService) {
        this.cartService = cartService;
        this.productService = productService;
        this.securityUtils = securityUtils;
        this.userService = userService;
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

    @PatchMapping("/client/carts/{id}")
    @ApiMessage("Increase or Decrease quanity product")
    public void setInDe(@PathVariable("id") Long id, @Valid @RequestBody RequestAddToCart request)
            throws IdInvalidException {
        this.cartService.setIncreaseOrDecreaseForProduct(id, request.getOperation(), request.getQuantity());
    }

    @DeleteMapping("/client/carts/{id}")
    @ApiMessage("Remove product from cart")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable("id") Long id) throws IdInvalidException {
        String email = this.securityUtils.getCurrentUserLogin().get();
        User user = this.userService.getUserByEmail(email);
        Cart cart = user.getCart();
        Product product = this.productService.fetch(id);
        CartDetail cartDetail = this.cartService.getCartDetailByCartAndProduct(cart, product);
        if (cartDetail == null) {
            throw new IdInvalidException("Product not exist in your cart!");
        }
        this.cartService.removeProductFromCart(id);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/client/carts/checkout")
    @ApiMessage("Checkout cart before order")
    public ResponseEntity<ResCartCheckoutDTO> checkout(@RequestBody RequestCheckoutCart re) throws IdInvalidException {
        return ResponseEntity.ok(this.cartService.checkoutCart(re));
    }

}
