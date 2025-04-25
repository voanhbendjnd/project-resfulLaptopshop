package com.laptopshopResful.domain.entity;

import java.time.Instant;
import java.util.List;

import org.apache.catalina.security.SecurityUtil;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.laptopshopResful.utils.SecurityUtils;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "discountUsers")
public class DiscountUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne
    @JoinColumn(name = "discountId")
    private Discount discount;

    private Instant usedAt;
    private String usedBy;

    @PrePersist
    public void handleBeforeCreateAt() {
        this.usedBy = SecurityUtils.getCurrentUserLogin().isPresent() == true
                ? SecurityUtils.getCurrentUserLogin().get()
                : "";
        this.usedAt = Instant.now();
    }
}
