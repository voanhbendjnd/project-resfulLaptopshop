package com.laptopshopResful.domain.entity;

import java.time.Instant;
import java.util.List;

import org.apache.catalina.security.SecurityUtil;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.laptopshopResful.utils.SecurityUtils;
import com.laptopshopResful.utils.constant.FiledDiscountEnum;
import com.laptopshopResful.utils.constant.TargetEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "discounts")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private Integer discount;
    private Integer frequency;
    @Enumerated(EnumType.STRING)
    private TargetEnum filed;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy, updatedBy;

    @OneToMany(mappedBy = "discount", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DiscountUser> discountUsers;

    @OneToMany(mappedBy = "discount", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<DiscountProduct> discountProducts;

    @PrePersist
    public void handleBeforeCreateAt() {
        this.createdBy = SecurityUtils.getCurrentUserLogin().isPresent() == true
                ? SecurityUtils.getCurrentUserLogin().get()
                : "";
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void handleBeforeUpdateBy() {
        this.updatedBy = SecurityUtils.getCurrentUserLogin().isPresent() == true
                ? SecurityUtils.getCurrentUserLogin().get()
                : "";
        this.updatedAt = Instant.now();
    }

}
