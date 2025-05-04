package com.laptopshopResful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laptopshopResful.domain.entity.Otp;

@Repository
public interface OptRepository extends JpaRepository<Otp, Long> {

}
