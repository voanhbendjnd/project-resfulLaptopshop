package com.laptopshopResful.domain.response.discount;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResDiscountUpdate extends DiscountDTO {
    private Instant updatedAt;
    private String updatedBy;
}
