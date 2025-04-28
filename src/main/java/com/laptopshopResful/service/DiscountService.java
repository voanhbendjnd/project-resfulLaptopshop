package com.laptopshopResful.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.laptopshopResful.domain.entity.Discount;
import com.laptopshopResful.domain.entity.DiscountUser;
import com.laptopshopResful.domain.entity.User;
import com.laptopshopResful.domain.response.ResultPaginationDTO;
import com.laptopshopResful.domain.response.discount.ResDiscountCreate;
import com.laptopshopResful.domain.response.discount.ResDiscountUpdate;
import com.laptopshopResful.repository.DiscountRepository;
import com.laptopshopResful.repository.DiscountUserRepository;
import com.laptopshopResful.utils.SecurityUtils;
import com.laptopshopResful.utils.UpdateNotNull;
import com.laptopshopResful.utils.convert.discount.ConvertDiscountToRes;

@Service
public class DiscountService {
    private final DiscountRepository discountRepository;
    private final SecurityUtils securityUtils;
    private final UserService userService;
    // private final DiscountUserRepository discountUserRepository;
    private final DiscountUserService discountUserService;

    public DiscountService(DiscountRepository discountRepository,
            SecurityUtils securityUtils,
            UserService userService,
            DiscountUserRepository discountUserRepository,
            DiscountUserService discountUserService) {
        this.discountRepository = discountRepository;
        this.securityUtils = securityUtils;
        this.userService = userService;
        this.discountUserService = discountUserService;
        // this.discountUserRepository = discountUserRepository;
    }

    public boolean existsById(Long id) {
        return this.discountRepository.existsById(id) ? true : false;
    }

    public ResDiscountCreate create(Discount dis) {
        this.discountRepository.save(dis);
        ResDiscountCreate res = new ResDiscountCreate();
        res.setDiscount(dis.getDiscount());
        res.setCode(dis.getCode());
        res.setCreatedAt(dis.getCreatedAt());
        res.setCreatedBy(dis.getCreatedBy());
        res.setFiled(dis.getFiled());
        res.setFrequency(dis.getFrequency());
        return res;
    }

    public ResDiscountUpdate update(Discount dis) {
        Discount discount = this.discountRepository.findById(dis.getId()).get();
        UpdateNotNull.handle(dis, discount);
        this.discountRepository.save(discount);
        return ConvertDiscountToRes.convertUpdate(discount);
    }

    public Discount fetchById(Long id) {
        return this.discountRepository.findById(id).get();
    }

    public void handleDiscoutAfter(Long id) {
        Discount dis = this.discountRepository.findById(id).get();
        String email = this.securityUtils.getCurrentUserLogin().get();
        User user = this.userService.getUserByEmail(email);
        DiscountUser disU = new DiscountUser();
        disU.setDiscount(dis);
        disU.setUser(user);
        this.discountUserService.save(disU);
        Integer newFr = dis.getFrequency() - 1;
        if (newFr <= 0) {
            this.discountRepository.delete(dis);
        } else {
            dis.setFrequency(newFr);
            this.discountRepository.save(dis);
        }
    }

    public Discount fetchByCode(String code) {
        return this.discountRepository.findByCode(code);
    }

    public boolean existsByCode(String code) {
        return this.discountRepository.existsByCode(code) ? true : false;
    }

    public ResultPaginationDTO fetchAll(Specification<Discount> spec, Pageable pageable) {
        Page<Discount> page = this.discountRepository.findAll(pageable);
        ResultPaginationDTO res = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();
        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());
        mt.setPages(page.getTotalPages());
        mt.setTotlal(page.getTotalElements());
        res.setMeta(mt);
        res.setResult(page.getContent());
        return res;
    }

    public void deleteById(Long id) {
        this.discountRepository.deleteById(id);
    }
}
