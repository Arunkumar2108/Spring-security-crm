package com.mahd.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahd.employee.models.Tickets;

@Repository
public interface TicketRepo extends JpaRepository<Tickets, Long> {

}
