package com.laptopshopResful.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.laptopshopResful.domain.entity.Permission;
import com.laptopshopResful.domain.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    Role findByName(String name);

    boolean existsByName(String name);

    List<Role> findByIdIn(List<Long> ids);

    boolean existsById(Long id);
}
