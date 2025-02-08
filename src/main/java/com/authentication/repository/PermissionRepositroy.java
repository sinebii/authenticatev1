package com.authentication.repository;

import com.authentication.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepositroy extends JpaRepository<Permission, Long> {
    Optional<Permission> findByName(String name);
}
