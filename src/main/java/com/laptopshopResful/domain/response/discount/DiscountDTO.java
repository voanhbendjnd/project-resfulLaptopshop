package com.laptopshopResful.domain.response.discount;

import java.time.Instant;

import com.laptopshopResful.utils.constant.FiledDiscountEnum;
import com.laptopshopResful.utils.constant.TargetEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDTO {
    private String code;
    private Integer discount;
    private Integer frequency;
    private TargetEnum filed;
}
