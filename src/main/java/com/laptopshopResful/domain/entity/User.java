package com.laptopshopResful.domain.entity;

import java.time.Instant;

import org.apache.catalina.security.SecurityUtil;

import com.laptopshopResful.utils.constant.GenderEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Table(name = "users")
@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name isn't empty!")
    private String name;
    private String email;
    private String password;

    private int age;

    @Enumerated(EnumType.STRING) // save data with varchar instead of integer
    private GenderEnum gender;

    private String address;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String refreshToken;

    private Instant createdAt;

    private Instant updatedAt;

    private String createdBy;

    private String updatedBy;

    // @PrePersist
    // public void handleBeforeCreateAt() {
    // this.createdBy = SecurityUtil.getCurrentUserLogin().isPresent() == true
    // ? SecurityUtil.getCurrentUserLogin().get()
    // : "";
    // this.createdAt = Instant.now();
    // }

    // @PreUpdate
    // public void handleBeforeUpdateBy() {
    // this.updatedBy = SecurityUtil.getCurrentUserLogin().isPresent() == true
    // ? SecurityUtil.getCurrentUserLogin().get()
    // : "";
    // this.updatedAt = Instant.now();
    // }

}
