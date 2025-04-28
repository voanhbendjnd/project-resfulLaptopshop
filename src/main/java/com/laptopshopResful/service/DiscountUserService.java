package com.laptopshopResful.service;

import org.springframework.stereotype.Service;

import com.laptopshopResful.domain.entity.DiscountUser;
import com.laptopshopResful.repository.DiscountUserRepository;

@Service
public class DiscountUserService {
    private final DiscountUserRepository discountUserRepository;

    public DiscountUserService(DiscountUserRepository discountUserRepository) {
        this.discountUserRepository = discountUserRepository;
    }

    public void save(DiscountUser dis) {
        this.discountUserRepository.save(dis);
    }

}
