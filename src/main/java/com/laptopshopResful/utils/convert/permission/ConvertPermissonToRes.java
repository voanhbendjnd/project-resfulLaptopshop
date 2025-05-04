package com.laptopshopResful.utils.convert.permission;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.laptopshopResful.domain.entity.Permission;
import com.laptopshopResful.domain.entity.Role;
import com.laptopshopResful.domain.response.permission.ResCreatePermissionDTO;
import com.laptopshopResful.domain.response.permission.ResFetchPermissionDTO;
import com.laptopshopResful.domain.response.permission.ResUpdatePermissionDTO;

public class ConvertPermissonToRes {
    public static ResCreatePermissionDTO convertToCreatRes(Permission permission) {
        ResCreatePermissionDTO res = new ResCreatePermissionDTO();
        res.setId(permission.getId());
        res.setName(permission.getName());
        res.setMethod(permission.getMethod());
        res.setModule(permission.getModule());
        res.setCreatedAt(permission.getCreatedAt());
        res.setApiPath(permission.getApiPath());
        res.setCreatedBy(permission.getCreatedBy());
        if (permission.getRoles() != null && !permission.getRoles().isEmpty()) {
            List<ResCreatePermissionDTO.RoleList> rolesList = permission.getRoles().stream()
                    .map(x -> new ResCreatePermissionDTO.RoleList(x.getId(), x.getName()))
                    .collect(Collectors.toList());
            res.setRoles(rolesList);
        }
        return res;
    }

    public static ResUpdatePermissionDTO convertToUpdateToRes(Permission permission) {
        ResUpdatePermissionDTO res = new ResUpdatePermissionDTO();
        res.setApiPath(permission.getApiPath());
        res.setId(permission.getId());
        res.setMethod(permission.getMethod());
        res.setModule(permission.getModule());
        res.setName(permission.getName());
        res.setUpdatedAt(permission.getUpdatedAt());
        res.setUpdatedBy(permission.getUpdatedBy());
        return res;
    }

    public static ResFetchPermissionDTO convertToFecthToRes(Permission permission) {
        ResFetchPermissionDTO res = new ResFetchPermissionDTO();
        res.setApiPath(permission.getApiPath());
        res.setId(permission.getId());
        res.setMethod(permission.getMethod());
        res.setModule(permission.getModule());
        res.setName(permission.getName());
        res.setCreatedAt(permission.getCreatedAt());
        res.setCreatedBy(permission.getCreatedBy());
        res.setUpdatedAt(permission.getUpdatedAt());
        res.setUpdatedBy(permission.getUpdatedBy());
        return res;
    }
}
