package com.laptopshopResful.utils.convert.role;

import java.util.List;
import java.util.stream.Collectors;

import com.laptopshopResful.domain.entity.Permission;
import com.laptopshopResful.domain.entity.Role;
import com.laptopshopResful.domain.response.role.ResCreateRoleDTO;
import com.laptopshopResful.domain.response.role.ResUpdateRoleDTO;

public class ConvertRoleToRes {
    public static ResCreateRoleDTO convertCreate(Role role) {
        ResCreateRoleDTO res = new ResCreateRoleDTO();
        res.setId(role.getId());
        res.setActive(role.getActive());
        res.setDescription(role.getDescription());
        res.setName(role.getName());
        res.setCreatedAt(role.getCreatedAt());
        res.setCreatedBy(role.getCreatedBy());
        if (role.getPermissions() != null) {
            List<String> name = role.getPermissions()
                    .stream()
                    .map(it -> it.getName())
                    .collect(Collectors.toList());
            res.setPermissions(name);
        }
        return res;
    }

    public static ResUpdateRoleDTO convertUpdate(Role role) {
        ResUpdateRoleDTO res = new ResUpdateRoleDTO();
        res.setId(role.getId());
        res.setActive(role.getActive());
        res.setDescription(role.getDescription());
        res.setName(role.getName());
        if (role.getPermissions() != null) {
            List<String> name = role.getPermissions()
                    .stream()
                    .map(it -> it.getName())
                    .collect(Collectors.toList());
            res.setPermissions(name);
        }
        res.setUpdatedAt(role.getUpdatedAt());
        res.setUpdatedBy(role.getCreatedBy());
        return res;
    }
}
