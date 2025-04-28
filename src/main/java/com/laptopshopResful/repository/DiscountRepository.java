package com.laptopshopResful.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.laptopshopResful.domain.entity.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long>, JpaSpecificationExecutor<Discount> {
    boolean existsById(Long id);

    List<Discount> findByCodeIn(List<String> code);

    boolean existsByCode(String code);

    Discount findByCode(String code);
}
