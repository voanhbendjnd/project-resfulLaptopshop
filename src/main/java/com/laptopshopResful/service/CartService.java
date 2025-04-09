package com.laptopshopResful.service;

import org.springframework.stereotype.Service;
import com.laptopshopResful.config.SercurityConfiguration;
import com.laptopshopResful.domain.entity.Cart;
import com.laptopshopResful.domain.entity.User;
import com.laptopshopResful.repository.CartRepository;
import com.laptopshopResful.repository.UserRepository;
import com.laptopshopResful.utils.SecurityUtils;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final SecurityUtils securityUtils;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, SecurityUtils securityUtils, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.securityUtils = securityUtils;
        this.userRepository = userRepository;
    }

    public Cart create(Cart cart) {
        String email = this.securityUtils.getCurrentUserLogin().isPresent()
                ? this.securityUtils.getCurrentUserLogin().get()
                : "";
        User user = this.userRepository.findByEmail(email);
        cart.setUser(user);
        return this.cartRepository.save(cart);
    }
}
