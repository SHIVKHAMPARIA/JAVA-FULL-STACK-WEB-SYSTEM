package com.example.usermanagement.repository;

import com.example.usermanagement.model.CourseItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseItemRepository extends JpaRepository<CourseItem, Long> {

	List<CourseItem> findByEnabled(boolean enabled);
}
