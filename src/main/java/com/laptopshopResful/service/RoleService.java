package com.laptopshopResful.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.laptopshopResful.controller.PermissionController;
import com.laptopshopResful.domain.entity.Permission;
import com.laptopshopResful.domain.entity.Role;
import com.laptopshopResful.domain.response.ResultPaginationDTO;
import com.laptopshopResful.domain.response.role.ResCreateRoleDTO;
import com.laptopshopResful.domain.response.role.ResUpdateRoleDTO;
import com.laptopshopResful.repository.PermissionRepository;
import com.laptopshopResful.repository.RoleRepository;
import com.laptopshopResful.utils.UpdateNotNull;
import com.laptopshopResful.utils.convert.role.ConvertRoleToRes;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public boolean existsById(Long id) {
        return this.roleRepository.existsById(id) ? true : false;
    }

    public ResCreateRoleDTO create(Role role) {
        if (role.getPermissions() != null) {
            List<Long> listId = role.getPermissions()
                    .stream()
                    .map(Permission::getId)
                    .collect(Collectors.toList());
            List<Permission> per = this.permissionRepository.findByIdIn(listId);
            role.setPermissions(per);
        }
        this.roleRepository.save(role);
        return ConvertRoleToRes.convertCreate(role);
    }

    public ResUpdateRoleDTO update(Role role) {
        Role r = this.roleRepository.findById(role.getId()).get();
        UpdateNotNull.handle(role, r);
        this.roleRepository.save(r);
        return ConvertRoleToRes.convertUpdate(role);
    }

    public Role fetchById(Long id) {
        return this.roleRepository.findById(id).get();
    }

    public ResultPaginationDTO fetchWithPagination(Pageable pageable, Specification<Role> spec) {
        Page<Role> page = this.roleRepository.findAll(spec, pageable);
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
        this.roleRepository.deleteById(id);
    }

    public boolean existsByName(String name) {
        return this.roleRepository.existsByName(name) ? true : false;
    }

}
