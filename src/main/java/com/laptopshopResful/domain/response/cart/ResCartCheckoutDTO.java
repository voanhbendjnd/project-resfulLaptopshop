package com.laptopshopResful.domain.response.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResCartCheckoutDTO {
    private Long quantity;
    private Long totalPrice;
}
