package com.laptopshopResful.domain.response.role;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResCreateRoleDTO extends RoleDTO {
    private Instant createdAt;
    private String createdBy;
}
