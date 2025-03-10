package com.laptopshopResful.domain.response.user;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResUpdateUserDTO extends UserDTO {
    private Instant updatedAt;
    private String updatedBy;
}
