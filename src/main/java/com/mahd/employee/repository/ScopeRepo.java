package com.mahd.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahd.employee.models.Scope;

@Repository
public interface ScopeRepo extends JpaRepository<Scope, Long> {
	Scope findByScopeName(String scopeName);
}
