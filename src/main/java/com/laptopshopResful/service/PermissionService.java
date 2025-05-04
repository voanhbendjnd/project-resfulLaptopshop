package com.laptopshopResful.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.laptopshopResful.domain.entity.Permission;
import com.laptopshopResful.domain.entity.Role;
import com.laptopshopResful.domain.response.ResultPaginationDTO;
import com.laptopshopResful.domain.response.permission.ResCreatePermissionDTO;
import com.laptopshopResful.domain.response.permission.ResUpdatePermissionDTO;
import com.laptopshopResful.repository.PermissionRepository;
import com.laptopshopResful.repository.RoleRepository;
import com.laptopshopResful.utils.UpdateNotNull;
import com.laptopshopResful.utils.convert.permission.ConvertPermissonToRes;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final RoleService roleService;

    public PermissionService(PermissionRepository permissionRepository, RoleService roleService) {
        this.permissionRepository = permissionRepository;
        this.roleService = roleService;
    }

    // public ResCreatePermissionDTO create(Permission per) {

    // Permission currentPermission = new Permission();
    // currentPermission.setApiPath(per.getApiPath());
    // currentPermission.setMethod(per.getMethod());
    // currentPermission.setModule(per.getModule());
    // currentPermission.setName(per.getName());
    // if (!roles.isEmpty() && roles != null) {
    // currentPermission.setRoles(roles);
    // }
    // return ConvertPermissonToRes.convertToCreatRes(per);
    // }

    public ResCreatePermissionDTO create(Permission per) {
        if (per.getRoles() != null) {
            List<Long> idRole = per.getRoles().stream()
                    .map(x -> x.getId())
                    .collect(Collectors.toList());
            List<Role> dbRoles = this.roleService.findByIdIn(idRole);
            per.setRoles(dbRoles);
        }
        Permission currentPermission = this.permissionRepository.save(per);
        return ConvertPermissonToRes.convertToCreatRes(currentPermission);

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

    public Permission fetchById(Long id) {
        return this.permissionRepository.findById(id).get();
    }

    public ResultPaginationDTO fetchWithPagination(Pageable pageable, Specification<Permission> spec) {
        Page<Permission> page = this.permissionRepository.findAll(spec, pageable);
        ResultPaginationDTO res = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();
        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());
        mt.setPages(page.getTotalPages());
        mt.setTotlal(page.getTotalElements());
        res.setMeta(mt);
        res.setResult(page.getContent());
        return res;
    }

    public void delete(Long id) {
        Permission per = this.permissionRepository.findById(id).get();
        per.getRoles()
                .forEach(it -> it.getPermissions().remove(per));
        this.permissionRepository.delete(per);
    }

    public boolean existsById(Long id) {
        return this.permissionRepository.existsById(id) ? true : false;
    }
}
