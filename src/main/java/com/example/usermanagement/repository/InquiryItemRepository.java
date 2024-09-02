package com.example.usermanagement.repository;

import com.example.usermanagement.model.InquiryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryItemRepository extends JpaRepository<InquiryItem, Long> {

    @Query("SELECT i FROM InquiryItem i WHERE " +
            "(:city IS NULL OR :city = '' OR i.city = :city) AND " +
            "(:state IS NULL OR :state = '' OR i.state = :state) AND " +
            "(:fullName IS NULL OR :fullName = '' OR i.fullName = :fullName) AND " +
            "(:id IS NULL OR i.id = :id) AND " +
            "(:district IS NULL OR :district = '' OR i.district = :district)")
    List<InquiryItem> findInquiries(@Param("city") String city,
                                    @Param("state") String state,
                                    @Param("fullName") String fullName,
                                    @Param("id") Long id,
                                    @Param("district") String district);
}
