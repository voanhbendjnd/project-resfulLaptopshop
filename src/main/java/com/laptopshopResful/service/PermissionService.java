package com.laptopshopResful.service;

import org.springframework.stereotype.Service;

import com.laptopshopResful.domain.entity.Permission;
import com.laptopshopResful.domain.response.permission.ResCreatePermissionDTO;
import com.laptopshopResful.repository.PermissionRepository;
import com.laptopshopResful.utils.convert.permission.ConvertPermissonToRes;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public ResCreatePermissionDTO create(Permission per) {
        this.permissionRepository.save(per);
        return ConvertPermissonToRes.convertToCreatRes(per);
    }
}
