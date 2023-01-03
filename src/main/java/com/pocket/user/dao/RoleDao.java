package com.pocket.user.dao;

import com.pocket.user.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDao extends JpaRepository<Role, String> {
    Optional<Role> findByRoleName(String role);
}
