package com.laptopshopResful.domain.entity;

import com.laptopshopResful.utils.constant.FactoryEnum;
import com.laptopshopResful.utils.constant.TargetEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String price;
    private Long quantity;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String detailDesc;
    private TargetEnum target;
    private String shortDesc;
    private String image;
    private Long sold;
    private FactoryEnum factory;
}
