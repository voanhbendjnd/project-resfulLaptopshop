package com.laptopshopResful.utils.convert.product;

import com.laptopshopResful.domain.entity.Product;
import com.laptopshopResful.domain.response.product.ResCreateProductDTO;
import com.laptopshopResful.domain.response.product.ResUpdateProductDTO;

public class ConvertProductToRes {
    public static ResCreateProductDTO create(Product product) {
        ResCreateProductDTO res = new ResCreateProductDTO();
        res.setId(product.getId());
        res.setName(product.getName());
        res.setPrice(product.getPrice());
        res.setQuantity(product.getQuantity());
        res.setShortDesc(product.getShortDesc());
        res.setSold(product.getSold());
        res.setDetailDesc(product.getDetailDesc());
        res.setImage(product.getImage());
        res.setTarget(product.getTarget());
        res.setFactory(product.getFactory());
        res.setCreatedAt(product.getCreatedAt());
        res.setCreatedBy(product.getCreatedBy());
        return res;
    }

    public static ResUpdateProductDTO update(Product product) {
        ResUpdateProductDTO res = new ResUpdateProductDTO();
        res.setId(product.getId());
        res.setName(product.getName());
        res.setPrice(product.getPrice());
        res.setQuantity(product.getQuantity());
        res.setShortDesc(product.getShortDesc());
        res.setSold(product.getSold());
        res.setDetailDesc(product.getDetailDesc());
        res.setImage(product.getImage());
        res.setTarget(product.getTarget());
        res.setFactory(product.getFactory());
        res.setUpdatedAt(product.getUpdatedAt());
        res.setUpdatedBy(product.getUpdatedBy());
        return res;
    }
}
