package com.bloodbank.repository;

import com.bloodbank.entity.BloodUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BloodUnitRepository extends JpaRepository<BloodUnit, Long> {

    // Find blood units by status
    List<BloodUnit> findByStatus(String status);

    // Find blood units by blood group and status
    List<BloodUnit> findByBloodGroupAndStatus(
            String bloodGroup,
            String status
    );

    // Find blood units expiring between two dates
    List<BloodUnit> findByExpiryDateBetweenAndStatus(
            LocalDate startDate,
            LocalDate endDate,
            String status
    );
}