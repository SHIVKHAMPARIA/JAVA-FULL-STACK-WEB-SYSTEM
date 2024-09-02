package com.example.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.usermanagement.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {	
    // No additional methods are needed here unless you want custom queries
}
