package com.laptopshopResful.utils.convert.user;

import com.laptopshopResful.domain.entity.User;
import com.laptopshopResful.domain.response.user.ResCreateUserDTO;
import com.laptopshopResful.domain.response.user.ResFetchUserDTO;
import com.laptopshopResful.domain.response.user.ResUpdateUserDTO;

public class ConvertUserToRes {
    public static ResCreateUserDTO convertToCreateRes(User user) {
        ResCreateUserDTO res = new ResCreateUserDTO();
        res.setId(user.getId());
        res.setName(user.getName());
        res.setAge(user.getAge());
        res.setEmail(user.getEmail());
        res.setGender(user.getGender());
        res.setAddress(user.getAddress());
        res.setCreatedAt(user.getCreatedAt());
        res.setCreatedBy(user.getCreatedBy());
        return res;
    }

    public static ResUpdateUserDTO convertToUpdateRes(User user) {
        ResUpdateUserDTO res = new ResUpdateUserDTO();
        res.setId(user.getId());
        res.setName(user.getName());
        res.setAge(user.getAge());
        res.setEmail(user.getEmail());
        res.setGender(user.getGender());
        res.setAddress(user.getAddress());
        res.setUpdatedAt(user.getUpdatedAt());
        res.setUpdatedBy(user.getUpdatedBy());
        return res;
    }

    public static ResFetchUserDTO convertToFetchRes(User user) {
        ResFetchUserDTO res = new ResFetchUserDTO();
        res.setId(user.getId());
        res.setName(user.getName());
        res.setAge(user.getAge());
        res.setEmail(user.getEmail());
        res.setGender(user.getGender());
        res.setAddress(user.getAddress());
        res.setUpdatedAt(user.getUpdatedAt());
        res.setUpdatedBy(user.getUpdatedBy());
        res.setCreatedAt(user.getCreatedAt());
        res.setCreatedBy(user.getCreatedBy());
        return res;

    }
}