package com.laptopshopResful.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.laptopshopResful.domain.entity.Permission;
import com.laptopshopResful.domain.response.ResultPaginationDTO;
import com.laptopshopResful.domain.response.permission.ResCreatePermissionDTO;
import com.laptopshopResful.domain.response.permission.ResUpdatePermissionDTO;
import com.laptopshopResful.repository.RoleRepository;
import com.laptopshopResful.service.PermissionService;
import com.laptopshopResful.service.RoleService;
import com.laptopshopResful.utils.annotation.ApiMessage;
import com.laptopshopResful.utils.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

@RestController
public class PermissionController {

    private final RoleService roleService;

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService, RoleRepository roleRepository,
            RoleService roleService) {
        this.permissionService = permissionService;
        this.roleService = roleService;
    }

    @PostMapping("/permissions")
    public ResponseEntity<ResCreatePermissionDTO> create(@Valid @RequestBody Permission per) throws IdInvalidException {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.permissionService.create(per));
    }

    @PutMapping("/permissions")
    @ApiMessage("Update permission")
    public ResponseEntity<ResUpdatePermissionDTO> update(@RequestBody Permission per) throws IdInvalidException {
        if (!this.permissionService.existsById(per.getId())) {
            throw new IdInvalidException("Id not already exists!");
        }
        return ResponseEntity.ok(this.permissionService.update(per));
    }

    @GetMapping("/permissions/{id}")
    @ApiMessage("Fetch by id")
    public ResponseEntity<Permission> fetchById(@PathVariable("id") Long id) throws IdInvalidException {
        if (!this.permissionService.existsById(id)) {
            throw new IdInvalidException("Id with " + id + " not exists!");
        }
        return ResponseEntity.ok(this.permissionService.fetchById(id));
    }

    @GetMapping("/permissions")
    @ApiMessage("Fetch with pagination")
    public ResponseEntity<ResultPaginationDTO> fetchWithPagination(@Filter Specification<Permission> spec,
            Pageable pageable) {
        return ResponseEntity.ok(this.permissionService.fetchWithPagination(pageable, spec));
    }

    @DeleteMapping("/permissions/{id}")
    @ApiMessage("Delete Permission")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) throws IdInvalidException {
        if (this.permissionService.existsById(id)) {
            this.roleService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        throw new IdInvalidException("Id with " + id + " not exists");
    }
}
