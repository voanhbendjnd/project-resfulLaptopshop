package com.laptopshopResful.utils.convert.role;

import com.laptopshopResful.domain.entity.Role;
import com.laptopshopResful.domain.response.role.ResCreateRoleDTO;
import com.laptopshopResful.domain.response.role.ResUpdateRoleDTO;

public class ConvertRoleToRes {
    public static ResCreateRoleDTO convertCreate(Role role) {
        ResCreateRoleDTO res = new ResCreateRoleDTO();
        res.setId(role.getId());
        res.setActive(role.isActive());
        res.setDescription(role.getDescription());
        res.setName(role.getName());
        res.setCreatedAt(role.getCreatedAt());
        res.setCreatedBy(role.getCreatedBy());
        return res;
    }

    public static ResUpdateRoleDTO convertUpdate(Role role) {
        ResUpdateRoleDTO res = new ResUpdateRoleDTO();
        res.setId(role.getId());
        res.setActive(role.isActive());
        res.setDescription(role.getDescription());
        res.setName(role.getName());
        res.setUpdatedAt(role.getUpdatedAt());
        res.setUpdatedBy(role.getCreatedBy());
        return res;
    }
}
