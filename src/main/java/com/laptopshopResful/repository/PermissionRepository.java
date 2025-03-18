package com.laptopshopResful.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.laptopshopResful.domain.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {
    boolean existsById(Long id);

    List<Permission> findByIdIn(List<Long> id);
}
