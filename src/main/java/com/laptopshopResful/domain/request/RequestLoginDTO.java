package com.laptopshopResful.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestLoginDTO {
    @NotBlank(message = "username không để trống đươc")
    private String username;
    private String password;

    @NotBlank(message = "password không được để trống")
    public String getUsername() {
        return username;
    }

}
