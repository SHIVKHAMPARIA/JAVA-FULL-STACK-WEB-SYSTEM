package com.example.usermanagement.repository;

import com.example.usermanagement.model.TestimonialItem;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestimonialItemRepository extends JpaRepository<TestimonialItem, Long> {


}
