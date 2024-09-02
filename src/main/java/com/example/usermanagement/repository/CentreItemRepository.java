package com.example.usermanagement.repository;

import com.example.usermanagement.model.CentreItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CentreItemRepository extends JpaRepository<CentreItem, Long> {

	List<CentreItem> findByEnabled(boolean enabled);
}
