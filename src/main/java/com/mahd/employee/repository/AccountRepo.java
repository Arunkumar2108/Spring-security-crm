package com.mahd.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahd.employee.models.Accounts;

import java.util.UUID;

@Repository
public interface AccountRepo extends JpaRepository<Accounts, Long> {
	
	Accounts findByCustomerName(String accountName);

}
