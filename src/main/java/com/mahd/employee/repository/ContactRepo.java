package com.mahd.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahd.employee.models.Contact;

@Repository
public interface ContactRepo extends JpaRepository<Contact, Long> {
	
	Contact findByContactName(String contactName);
	
}
