package com.laptopshopResful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laptopshopResful.domain.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
