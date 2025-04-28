package com.laptopshopResful.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.laptopshopResful.domain.entity.Product;
import com.laptopshopResful.domain.response.ResultPaginationDTO;
import com.laptopshopResful.domain.response.product.ResCreateProductDTO;
import com.laptopshopResful.domain.response.product.ResUpdateProductDTO;
import com.laptopshopResful.repository.ProductRepository;
import com.laptopshopResful.utils.UpdateNotNull;
import com.laptopshopResful.utils.convert.product.ConvertProductToRes;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public boolean existsById(long id) {
        return this.productRepository.existsById(id) ? true : false;
    }

    public ResCreateProductDTO create(Product product) {
        this.productRepository.save(product);
        return ConvertProductToRes.create(product);
    }

    public ResUpdateProductDTO update(Product product) {
        Product pro = this.productRepository.findById(product.getId()).get();
        UpdateNotNull.handle(product, pro);
        this.productRepository.save(pro);
        return ConvertProductToRes.update(pro);
    }

    public Product fetch(Long id) {
        return this.productRepository.findById(id).get();
    }

    public void delete(Long id) {
        this.productRepository.deleteById(id);
    }

    public Product findById(Long id) {
        return this.productRepository.findById(id).get();
    }

    public ResultPaginationDTO fetchAll(Pageable pageable, Specification<Product> spec) {
        ResultPaginationDTO res = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();
        Page<Product> page = this.productRepository.findAll(spec, pageable);
        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());
        mt.setPages(page.getTotalPages());
        mt.setTotlal(page.getTotalElements());
        res.setMeta(mt);
        res.setResult(page.getContent());
        return res;
    }
}
