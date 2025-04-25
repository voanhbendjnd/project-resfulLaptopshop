package com.laptopshopResful.controller.admin;

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

import com.laptopshopResful.domain.entity.Role;
import com.laptopshopResful.domain.response.ResultPaginationDTO;
import com.laptopshopResful.domain.response.role.ResCreateRoleDTO;
import com.laptopshopResful.domain.response.role.ResUpdateRoleDTO;
import com.laptopshopResful.service.RoleService;
import com.laptopshopResful.utils.annotation.ApiMessage;
import com.laptopshopResful.utils.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

@RestController
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/roles")
    @ApiMessage("Create new a role")
    public ResponseEntity<ResCreateRoleDTO> create(@Valid @RequestBody Role role) throws IdInvalidException {
        if (this.roleService.existsByName(role.getName())) {
            throw new IdInvalidException("Role with name " + role.getName() + " already exists!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(this.roleService.create(role));
    }

    @PutMapping("/roles")
    @ApiMessage("Update role")
    public ResponseEntity<ResUpdateRoleDTO> update(@RequestBody Role role) throws IdInvalidException {
        if (this.roleService.existsById(role.getId())) {
            return ResponseEntity.ok(this.roleService.update(role));
        }
        throw new IdInvalidException("Role with id " + role.getId() + " not exists!");
    }

    @GetMapping("/roles/{id}")
    @ApiMessage("Fetch role by id")
    public ResponseEntity<Role> fetch(@PathVariable("id") Long id) throws IdInvalidException {
        if (this.roleService.existsById(id)) {

            return ResponseEntity.ok(this.roleService.fetchById(id));
        }
        throw new IdInvalidException("Id with " + id + " not exits!");
    }

    @DeleteMapping("/roles/{id}")
    @ApiMessage("Delete by id")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) throws IdInvalidException {
        if (this.roleService.existsById(id)) {
            this.roleService.delete(id);
            return ResponseEntity.ok(null);
        }
        throw new IdInvalidException("Id with  " + id + " not exists!");
    }

    @GetMapping("/roles")
    @ApiMessage("Fetch with pagination")
    public ResponseEntity<ResultPaginationDTO> fetchAll(@Filter Specification<Role> spec, Pageable pageable) {
        return ResponseEntity.ok(this.roleService.fetchWithPagination(pageable, spec));
    }
}
