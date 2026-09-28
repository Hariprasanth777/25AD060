package com.bloodbank.repository;

import com.bloodbank.entity.BloodUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BloodUnitRepository extends JpaRepository<BloodUnit, Long> {

    List<BloodUnit> findByStatus(String status);

    List<BloodUnit> findByBloodGroupAndStatus(
            String bloodGroup,
            String status
    );

    List<BloodUnit> findByExpiryDateBetween(
            LocalDate startDate,
            LocalDate endDate
    );
}