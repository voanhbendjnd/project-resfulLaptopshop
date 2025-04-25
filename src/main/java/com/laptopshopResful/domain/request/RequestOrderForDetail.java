package com.laptopshopResful.domain.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class RequestOrderForDetail {
    private String name;
    private String address;
    private String phone;
    private Double totalPrice;
    private List<Items> items;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Items {
        private Long idProduct;
        private Long quantity;
        private String code;
    }

}
