package com.laptopshopResful.domain.response.product;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResUpdateProductDTO extends ProductDTO {
    private Instant updatedAt;
    private String updatedBy;
}
