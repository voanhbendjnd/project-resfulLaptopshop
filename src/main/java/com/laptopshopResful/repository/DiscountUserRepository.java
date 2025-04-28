package com.laptopshopResful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laptopshopResful.domain.entity.DiscountUser;

@Repository
public interface DiscountUserRepository extends JpaRepository<DiscountUser, Long> {

}
