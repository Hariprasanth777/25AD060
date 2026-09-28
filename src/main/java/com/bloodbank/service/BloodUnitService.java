package com.bloodbank.service;

import com.bloodbank.entity.BloodUnit;
import com.bloodbank.repository.BloodUnitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BloodUnitService {

    private final BloodUnitRepository bloodUnitRepository;

    public BloodUnitService(BloodUnitRepository bloodUnitRepository) {
        this.bloodUnitRepository = bloodUnitRepository;
    }

    // Add a blood unit
    public BloodUnit addBloodUnit(BloodUnit bloodUnit) {
        return bloodUnitRepository.save(bloodUnit);
    }

    // Get all blood units
    public List<BloodUnit> getAllBloodUnits() {
        return bloodUnitRepository.findAll();
    }

    // Get blood unit by ID
    public BloodUnit getBloodUnitById(Long id) {
        return bloodUnitRepository.findById(id).orElse(null);
    }

    // Get available stock by blood group
    public Map<String, Long> getStockByBloodGroup() {

        List<BloodUnit> availableUnits =
                bloodUnitRepository.findByStatus("AVAILABLE");

        Map<String, Long> stock = new HashMap<>();

        for (BloodUnit unit : availableUnits) {

            String bloodGroup = unit.getBloodGroup();

            // Ignore old/test units with missing blood group
            if (bloodGroup == null || bloodGroup.isBlank()) {
                continue;
            }

            stock.put(
                    bloodGroup,
                    stock.getOrDefault(bloodGroup, 0L) + 1
            );
        }

        return stock;
    }

    // Get blood units expiring within 7 days
    public List<BloodUnit> getExpiringBloodUnits() {

        LocalDate today = LocalDate.now();

        LocalDate sevenDaysLater = today.plusDays(7);

        return bloodUnitRepository.findByExpiryDateBetweenAndStatus(
                today,
                sevenDaysLater,
                "AVAILABLE"
        );
    }

    // Get complete inventory summary
    public Map<String, Object> getInventorySummary() {

        List<BloodUnit> availableUnits =
                bloodUnitRepository.findByStatus("AVAILABLE");

        Map<String, Long> stock = new HashMap<>();

        for (BloodUnit unit : availableUnits) {

            String bloodGroup = unit.getBloodGroup();

            // Ignore units with missing blood group
            if (bloodGroup == null || bloodGroup.isBlank()) {
                continue;
            }

            stock.put(
                    bloodGroup,
                    stock.getOrDefault(bloodGroup, 0L) + 1
            );
        }

        Map<String, Object> summary = new HashMap<>();

        summary.put(
                "totalAvailableUnits",
                availableUnits.size()
        );

        summary.put(
                "bloodGroupStock",
                stock
        );

        summary.put(
                "expiringUnits",
                getExpiringBloodUnits().size()
        );

        return summary;
    }
}