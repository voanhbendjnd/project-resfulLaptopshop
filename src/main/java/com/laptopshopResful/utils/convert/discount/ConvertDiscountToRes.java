package com.laptopshopResful.utils.convert.discount;

import com.laptopshopResful.domain.entity.Discount;
import com.laptopshopResful.domain.response.discount.ResDiscountCreate;
import com.laptopshopResful.domain.response.discount.ResDiscountUpdate;

public class ConvertDiscountToRes {
    public static ResDiscountCreate convertCreate(Discount dis) {
        ResDiscountCreate res = new ResDiscountCreate();
        res.setCode(dis.getCode());
        res.setCreatedAt(dis.getCreatedAt());
        res.setCreatedBy(dis.getCreatedBy());
        res.setDiscount(dis.getDiscount());
        res.setFiled(dis.getFiled());
        res.setFrequency(dis.getFrequency());
        return res;
    }

    public static ResDiscountUpdate convertUpdate(Discount dis) {
        ResDiscountUpdate res = new ResDiscountUpdate();
        res.setCode(dis.getCode());
        res.setUpdatedAt(dis.getUpdatedAt());
        res.setUpdatedBy(dis.getUpdatedBy());
        res.setDiscount(dis.getDiscount());
        res.setFiled(dis.getFiled());
        res.setFrequency(dis.getFrequency());
        return res;
    }
}
