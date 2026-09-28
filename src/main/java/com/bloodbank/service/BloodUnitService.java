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

    public BloodUnit addBloodUnit(BloodUnit bloodUnit) {
        return bloodUnitRepository.save(bloodUnit);
    }

    public List<BloodUnit> getAllBloodUnits() {
        return bloodUnitRepository.findAll();
    }

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

        return bloodUnitRepository.findByExpiryDateBetween(
                today,
                sevenDaysLater
        );
    }
}