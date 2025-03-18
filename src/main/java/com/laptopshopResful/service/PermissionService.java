package com.laptopshopResful.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import com.laptopshopResful.domain.entity.Permission;
import com.laptopshopResful.domain.response.permission.ResCreatePermissionDTO;
import com.laptopshopResful.domain.response.permission.ResUpdatePermissionDTO;
import com.laptopshopResful.repository.PermissionRepository;
import com.laptopshopResful.utils.UpdateNotNull;
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

    public ResUpdatePermissionDTO update(Permission per) {
        Permission perNew = this.permissionRepository.findById(per.getId()).get();
        UpdateNotNull.handle(per, perNew);
        // perNew.get().setApiPath(per.getApiPath());
        // perNew.get().setName(per.getName());
        // perNew.get().setMethod(per.getMethod());
        // perNew.get().setModule(per.getModule());
        // this.permissionRepository.save(perNew.get());
        this.permissionRepository.save(perNew);
        return ConvertPermissonToRes.convertToUpdateToRes(perNew);

    }

    public boolean existsById(Long id) {
        return this.permissionRepository.existsById(id) ? true : false;
    }
}
