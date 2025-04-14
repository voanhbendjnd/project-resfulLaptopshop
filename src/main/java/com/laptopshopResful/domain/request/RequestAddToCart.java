package com.laptopshopResful.domain.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestAddToCart {
    @NotNull
    @Min(value = 1, message = "Quantity must greater then or equal to 1!")
    private Long quantity;

    private String operation;

}
