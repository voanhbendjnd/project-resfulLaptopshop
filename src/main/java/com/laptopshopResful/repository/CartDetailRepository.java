package com.laptopshopResful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laptopshopResful.domain.entity.Cart;
import com.laptopshopResful.domain.entity.CartDetail;
import com.laptopshopResful.domain.entity.Product;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    CartDetail findByCartAndProduct(Cart cart, Product product);
}
