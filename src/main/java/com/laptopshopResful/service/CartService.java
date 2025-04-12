package com.laptopshopResful.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import com.laptopshopResful.config.SercurityConfiguration;
import com.laptopshopResful.domain.entity.Cart;
import com.laptopshopResful.domain.entity.CartDetail;
import com.laptopshopResful.domain.entity.Product;
import com.laptopshopResful.domain.entity.User;
import com.laptopshopResful.repository.CartDetailRepository;
import com.laptopshopResful.repository.CartRepository;
import com.laptopshopResful.repository.ProductRepository;
import com.laptopshopResful.repository.UserRepository;
import com.laptopshopResful.utils.SecurityUtils;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final SecurityUtils securityUtils;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartDetailRepository cartDetailRepository;

    public CartService(CartRepository cartRepository, SecurityUtils securityUtils, UserRepository userRepository,
            ProductRepository productRepository, CartDetailRepository cartDetailRepository) {
        this.cartRepository = cartRepository;
        this.securityUtils = securityUtils;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartDetailRepository = cartDetailRepository;
    }

    public Cart create(Cart cart) {
        String email = this.securityUtils.getCurrentUserLogin().isPresent()
                ? this.securityUtils.getCurrentUserLogin().get()
                : "";
        User user = this.userRepository.findByEmail(email);
        cart.setUser(user);
        return this.cartRepository.save(cart);
    }

    public void addToCart(Long id, Long quantity) {
        if (this.productRepository.existsById(id)) {
            String email = this.securityUtils.getCurrentUserLogin().isPresent()
                    ? this.securityUtils.getCurrentUserLogin().get()
                    : "";
            User user = this.userRepository.findByEmail(email);
            Cart cart = user.getCart();
            Product product = this.productRepository.findById(id).get();
            CartDetail cd = this.cartDetailRepository.findByCartAndProduct(cart, product);
            if (cd != null) {
                cd.setQuantity(cd.getQuantity() + quantity);
                this.cartDetailRepository.save(cd);
            } else {
                CartDetail newCartDetail = new CartDetail();
                cart.setSum(cart.getSum() + 1);
                newCartDetail.setCart(cart);
                newCartDetail.setProduct(product);
                newCartDetail.setQuantity(quantity);
                newCartDetail.setPrice(product.getPrice() * 1.0);
                this.cartDetailRepository.save(newCartDetail);
            }
        }
    }

    public void create(User user) {
        Cart newCart = new Cart();
        newCart.setSum(0L);
        newCart.setUser(user);
        this.cartRepository.save(newCart);
    }

    public void createCart(Cart cart) {
        String email = this.securityUtils.getCurrentUserLogin().isPresent()
                ? this.securityUtils.getCurrentUserLogin().get()
                : "";
        User user = this.userRepository.findByEmail(email);
        cart.setUser(user);
        this.cartRepository.save(cart);
    }

}
