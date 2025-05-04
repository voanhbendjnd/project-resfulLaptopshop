package com.laptopshopResful.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

import com.laptopshopResful.utils.constant.TargetEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCheckoutCart {
    List<OrderItem> items;

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItem {
        private Long productId;
        private Long quantity;
        private String codeDiscount;
        private TargetEnum target;
    }
}
