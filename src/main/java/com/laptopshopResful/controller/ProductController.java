package com.laptopshopResful.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.laptopshopResful.domain.entity.Product;
import com.laptopshopResful.domain.response.ResultPaginationDTO;
import com.laptopshopResful.domain.response.product.ResCreateProductDTO;
import com.laptopshopResful.domain.response.product.ResUpdateProductDTO;
import com.laptopshopResful.service.ProductService;
import com.laptopshopResful.utils.annotation.ApiMessage;
import com.laptopshopResful.utils.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    @ApiMessage("Create new a product")
    public ResponseEntity<ResCreateProductDTO> create(@Valid @RequestBody Product product) throws IdInvalidException {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.productService.create(product));
    }

    @PutMapping("/products")
    @ApiMessage("Update a product")
    public ResponseEntity<ResUpdateProductDTO> update(@RequestBody Product product) throws IdInvalidException {
        if (this.productService.existsById(product.getId())) {
            return ResponseEntity.ok(this.productService.update(product));
        }
        throw new IdInvalidException("Id product with " + product.getId() + " not exists!");
    }

    @GetMapping("/products/{id}")
    @ApiMessage("Fetch product")
    public ResponseEntity<Product> fetch(@PathVariable("id") Long id) throws IdInvalidException {
        if (this.productService.existsById(id)) {
            return ResponseEntity.ok(this.productService.fetch(id));
        }
        throw new IdInvalidException("Id product with " + id + " not exists!");
    }

    @DeleteMapping("/products/{id}")
    @ApiMessage("Delete product")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) throws IdInvalidException {
        if (this.productService.existsById(id)) {
            this.productService.delete(id);
            return ResponseEntity.ok(null);
        }
        throw new IdInvalidException("Id product with " + id + " not exists");
    }

    @GetMapping("/products")
    @ApiMessage("Fetch with pagination")
    public ResponseEntity<ResultPaginationDTO> fetchAll(@Filter Specification<Product> spec, Pageable pageable) {
        return ResponseEntity.ok(this.productService.fetchAll(pageable, spec));
    }
}
