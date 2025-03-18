package com.laptopshopResful.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.laptopshopResful.domain.entity.Permission;
import com.laptopshopResful.domain.response.permission.ResCreatePermissionDTO;
import com.laptopshopResful.domain.response.permission.ResUpdatePermissionDTO;
import com.laptopshopResful.service.PermissionService;
import com.laptopshopResful.utils.annotation.ApiMessage;
import com.laptopshopResful.utils.error.IdInvalidException;

import jakarta.validation.Valid;

@RestController
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/permissions")
    public ResponseEntity<ResCreatePermissionDTO> create(@Valid @RequestBody Permission per) throws IdInvalidException {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.permissionService.create(per));
    }

    @PutMapping("/permissions")
    @ApiMessage("Update permission")
    public ResponseEntity<ResUpdatePermissionDTO> update(@Valid @RequestBody Permission per) throws IdInvalidException {
        if (!this.permissionService.existsById(per.getId())) {
            throw new IdInvalidException("Id not already exists!");
        }
        return ResponseEntity.ok(this.permissionService.update(per));
    }
}
