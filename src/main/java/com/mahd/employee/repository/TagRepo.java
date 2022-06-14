package com.mahd.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahd.employee.models.Tag;

import java.util.List;

@Repository
public interface TagRepo extends JpaRepository<Tag, Long> {
	Tag findByTagName(String tagName);
	List<Tag> findAllByDeleted(Boolean value);
}
