package com.mahd.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahd.employee.models.Company;

import java.util.List;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Long> {

	Company findByCompanyName(String name);
	List<Company> findAllByDeleted(Boolean value);
}
