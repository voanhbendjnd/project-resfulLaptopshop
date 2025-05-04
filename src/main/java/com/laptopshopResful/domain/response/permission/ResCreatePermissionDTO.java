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
public class ResCreatePermissionDTO extends PermissionDTO {
    private Instant createdAt;
    private String createdBy;
    private List<RoleList> roles;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RoleList {
        private Long id;
        private String name;
    }
}
