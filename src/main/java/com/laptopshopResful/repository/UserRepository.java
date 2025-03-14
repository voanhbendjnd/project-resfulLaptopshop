package com.laptopshopResful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.laptopshopResful.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    boolean existsByEmail(String email);

    User findByEmail(String email);

    boolean existsById(long id);

    User findByRefreshTokenAndEmail(String refresh, String email);
}
