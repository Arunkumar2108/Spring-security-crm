package com.mahd.employee.repository;

import com.mahd.employee.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahd.employee.models.Pipeline;

import java.util.List;

@Repository
public interface PipelineRepo extends JpaRepository<Pipeline, Long> {

    List<Pipeline> findAllByDeleted(Boolean value);
}
