package com.laptopshopResful.domain.response.product;

import com.laptopshopResful.utils.constant.FactoryEnum;
import com.laptopshopResful.utils.constant.TargetEnum;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String name;
    private Long price;
    private Long quantity;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String detailDesc;
    private TargetEnum target;
    private String shortDesc;
    private String image;
    private Long sold;
    private FactoryEnum factory;
}
