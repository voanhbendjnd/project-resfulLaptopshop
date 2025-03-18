package com.laptopshopResful.domain.response.role;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResUpdateRoleDTO extends RoleDTO {

    private Instant updatedAt;
    private String updatedBy;
}
