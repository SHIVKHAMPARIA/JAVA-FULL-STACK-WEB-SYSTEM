package com.example.usermanagement.repository;

import com.example.usermanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserItemRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE " +
            "(:city IS NULL OR :city = '' OR u.city = :city) AND " +
            "(:state IS NULL OR :state = '' OR u.state = :state) AND " +
            "(:username IS NULL OR :username = '' OR u.username = :username) AND " +
            "(:id IS NULL OR u.id = :id) AND " +
            "(:gender IS NULL OR :gender = '' OR u.gender = :gender)")
    List<User> findUsers(@Param("city") String city,
                         @Param("state") String state,
                         @Param("username") String username,
                         @Param("id") Long id,
                         @Param("gender") String gender);
}

