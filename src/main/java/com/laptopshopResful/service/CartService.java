package com.laptopshopResful.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.laptopshopResful.config.SercurityConfiguration;
import com.laptopshopResful.domain.entity.Cart;
import com.laptopshopResful.domain.entity.CartDetail;
import com.laptopshopResful.domain.entity.Product;
import com.laptopshopResful.domain.entity.User;
import com.laptopshopResful.domain.request.RequestCheckoutCart;
import com.laptopshopResful.domain.response.cart.ResCartCheckoutDTO;
import com.laptopshopResful.domain.response.cart.ResCartDTO;
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

    public ResCartDTO getCartDetail() {
        String email = this.securityUtils.getCurrentUserLogin().get();
        User user = this.userRepository.findByEmail(email);
        Cart cart = user.getCart();
        List<CartDetail> cartDetails = cart.getCartDetails();
        List<ResCartDTO.InnerResCartDTO> res = cartDetails.stream()
                .map(x -> {
                    Product product = x.getProduct();
                    ResCartDTO.InnerResCartDTO inner = new ResCartDTO.InnerResCartDTO();
                    inner.setPicture(product.getImage());
                    inner.setName(product.getName());
                    inner.setPrice(product.getPrice());
                    inner.setQuantity(x.getQuantity());
                    inner.setTotal(product.getPrice() * x.getQuantity());
                    return inner;
                }).collect(Collectors.toList());

        ResCartDTO resCartDTO = new ResCartDTO();
        resCartDTO.setProList(res);
        return resCartDTO;
    }

    public void deleteQuantityCart(Boolean check) {
        String email = this.securityUtils.getCurrentUserLogin().get();
        User user = this.userRepository.findByEmail(email);
        Cart cart = user.getCart();
        if (!check) {
            if (cart.getSum() - 1 <= 0) {
                cart.setSum(0L);
            }
        }
        this.cartRepository.save(cart);

    }

    public void setIncreaseOrDecreaseForProduct(Long id, String operation, Long quantity) {
        Product product = this.productRepository.findById(id).get();
        Long qtyProduct = product.getQuantity();
        String email = this.securityUtils.getCurrentUserLogin().get();
        User user = this.userRepository.findByEmail(email);
        Cart cart = user.getCart();
        CartDetail cartDetail = this.cartDetailRepository.findByCartAndProduct(cart, product);
        if (operation.equals("increase")) {
            Long currentQty = cartDetail.getQuantity() + quantity;
            if (currentQty > qtyProduct) {
                return;
            } else {
                cartDetail.setQuantity(currentQty);
                this.cartDetailRepository.save(cartDetail);

            }
        } else if (operation.equals("decrease")) {
            Long currentQty = cartDetail.getQuantity() - quantity;
            if (currentQty <= 0) {
                this.cartDetailRepository.delete(cartDetail);
                this.deleteQuantityCart(false);
            } else {
                cartDetail.setQuantity(currentQty);
                this.cartDetailRepository.save(cartDetail);

            }
        }
    }

    public void removeProductFromCart(Long id) {
        String email = this.securityUtils.getCurrentUserLogin().get();
        User user = this.userRepository.findByEmail(email);
        Cart cart = user.getCart();
        Product product = this.productRepository.findById(id).get();
        CartDetail cartDetail = this.cartDetailRepository.findByCartAndProduct(cart, product);
        cart.setSum(0L);
        this.cartRepository.save(cart);
        this.cartDetailRepository.delete(cartDetail);
    }

    public CartDetail getCartDetailByCartAndProduct(Cart cart, Product product) {
        return this.cartDetailRepository.findByCartAndProduct(cart, product);
    }

    public ResCartCheckoutDTO checkoutCart(RequestCheckoutCart checkout) {
        Long totalPrice = 0L;
        for (RequestCheckoutCart.OrderItem x : checkout.getItems()) {
            Product product = this.productRepository.findById(x.getProductId()).get();
            totalPrice += product.getPrice() * x.getQuantity();
        }
        ResCartCheckoutDTO res = new ResCartCheckoutDTO();
        res.setQuantity((checkout.getItems().size()) * 1L);
        res.setTotalPrice(totalPrice);
        return res;
    }

}
