package com.laptopshopResful.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class RequestOrderForDetail {
    private Long id;
    // private Double price;
    private Long quantity;
}
