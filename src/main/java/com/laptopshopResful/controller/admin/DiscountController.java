package com.laptopshopResful.controller.admin;

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

import com.laptopshopResful.domain.entity.Discount;
import com.laptopshopResful.domain.response.ResultPaginationDTO;
import com.laptopshopResful.domain.response.discount.ResDiscountCreate;
import com.laptopshopResful.domain.response.discount.ResDiscountUpdate;
import com.laptopshopResful.service.DiscountService;
import com.laptopshopResful.utils.annotation.ApiMessage;
import com.laptopshopResful.utils.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

@RestController
// @RequestMapping("/")
public class DiscountController {
    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping("/discounts")
    @ApiMessage("Create a new discount")
    public ResponseEntity<ResDiscountCreate> create(@RequestBody Discount dis) throws IdInvalidException {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.discountService.create(dis));
    }

    @PutMapping("/discounts")
    @ApiMessage("Update a a discount")
    public ResponseEntity<ResDiscountUpdate> update(@RequestBody Discount dis) throws IdInvalidException {
        if (!this.discountService.existsById(dis.getId())) {
            throw new IdInvalidException("Id discount not exists");
        }
        return ResponseEntity.ok(this.discountService.update(dis));
    }

    @GetMapping("/discounts/{id}")
    @ApiMessage("Fetch by id")
    public ResponseEntity<Discount> fetch(@PathVariable("id") Long id) throws IdInvalidException {
        if (this.discountService.existsById(id)) {
            return ResponseEntity.ok(this.discountService.fetchById(id));
        }
        throw new IdInvalidException("Discount not exists in system");
    }

    @GetMapping("/discounts")
    @ApiMessage("Fetch All")
    public ResponseEntity<ResultPaginationDTO> fetchAll(@Filter Specification<Discount> dis, Pageable page)
            throws IdInvalidException {
        return ResponseEntity.ok(this.discountService.fetchAll(dis, page));
    }

    @DeleteMapping("/discounts/{id}")
    @ApiMessage("Delete by id")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) throws IdInvalidException {
        if (!this.discountService.existsById(id))
            throw new IdInvalidException("Id with " + id + "not exist in system!");
        this.discountService.deleteById(id);
        return ResponseEntity.ok(null);
    }
}
