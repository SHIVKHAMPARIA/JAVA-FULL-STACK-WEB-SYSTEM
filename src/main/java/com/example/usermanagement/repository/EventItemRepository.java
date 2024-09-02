package com.example.usermanagement.repository;

import com.example.usermanagement.model.EventItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//import java.util.List;

@Repository
public interface EventItemRepository extends JpaRepository<EventItem, Long> {

	List<EventItem> findByEnabled(boolean b);
    //List<EventItem> findByEnabled(boolean enabled);
}
