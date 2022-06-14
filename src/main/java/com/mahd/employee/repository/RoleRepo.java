package com.mahd.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahd.employee.models.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
	Role findByRoleName(String roleName);
}
