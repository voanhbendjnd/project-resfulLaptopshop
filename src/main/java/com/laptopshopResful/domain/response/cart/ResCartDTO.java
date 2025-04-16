package com.laptopshopResful.domain.response.cart;

import java.lang.annotation.Target;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResCartDTO {
    private List<InnerResCartDTO> proList;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InnerResCartDTO {
        private String picture;
        private String name;
        private Long price;
        private Long quantity;
        private Long total;

    }
}
