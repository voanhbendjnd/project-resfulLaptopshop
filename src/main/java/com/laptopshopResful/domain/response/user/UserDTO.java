package com.laptopshopResful.domain.response.user;

import com.laptopshopResful.utils.constant.GenderEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    private String name;

    private String email;

    private String address;

    private GenderEnum gender;

    private int age;

}
