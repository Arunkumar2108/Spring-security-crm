package com.mahd.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahd.employee.models.Team;

@Repository
public interface TeamRepo extends JpaRepository<Team, Long> {

}
