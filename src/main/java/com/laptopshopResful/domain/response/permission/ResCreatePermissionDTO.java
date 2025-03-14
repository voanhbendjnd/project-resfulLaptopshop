package com.laptopshopResful.domain.response.permission;

import java.time.Instant;

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
}
