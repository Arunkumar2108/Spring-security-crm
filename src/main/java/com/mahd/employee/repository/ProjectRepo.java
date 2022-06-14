package com.mahd.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahd.employee.models.Project;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {

}
