package com.laptopshopResful.domain.response.permission;

import java.time.Instant;
import java.util.List;

import com.laptopshopResful.domain.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDTO {
    private Long id;
    private String name;
    private String apiPath;
    private String method;
    private String module;

}
