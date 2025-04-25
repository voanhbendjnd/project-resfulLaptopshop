package com.laptopshopResful.domain.response.order;

import com.laptopshopResful.utils.constant.StatusEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResOrderDTO {
    private String name, address, phone;
    private Double totalPrice;
    private StatusEnum status;
}
