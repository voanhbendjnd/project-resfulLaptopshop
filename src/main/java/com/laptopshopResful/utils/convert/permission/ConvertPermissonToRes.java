package com.laptopshopResful.utils.convert.permission;

import com.laptopshopResful.domain.entity.Permission;
import com.laptopshopResful.domain.response.permission.ResCreatePermissionDTO;

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
        return res;
    }
}
