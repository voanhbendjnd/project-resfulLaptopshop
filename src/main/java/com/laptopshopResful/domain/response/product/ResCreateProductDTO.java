package com.laptopshopResful.domain.response.product;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResCreateProductDTO extends ProductDTO {
    private Instant createdAt;
    private String createdBy;
}
