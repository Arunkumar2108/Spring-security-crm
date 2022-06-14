package com.mahd.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahd.employee.models.CannedMessage;

@Repository
public interface CannedMsgRepo extends JpaRepository<CannedMessage, Long> {

}
