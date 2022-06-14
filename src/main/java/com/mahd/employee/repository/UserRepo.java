package com.mahd.employee.repository;

import com.mahd.employee.models.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahd.employee.models.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{

	User findByUserName(String username);
	List<User> findAllByDeleted(Boolean value);
}
