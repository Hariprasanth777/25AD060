package com.bloodbank.controller;

import com.bloodbank.entity.BloodUnit;
import com.bloodbank.service.BloodUnitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/blood-units")
public class BloodUnitController {

    private final BloodUnitService bloodUnitService;

    public BloodUnitController(BloodUnitService bloodUnitService) {
        this.bloodUnitService = bloodUnitService;
    }

    // Add blood unit
    @PostMapping
    public BloodUnit addBloodUnit(@RequestBody BloodUnit bloodUnit) {
        return bloodUnitService.addBloodUnit(bloodUnit);
    }

    // Get all blood units
    @GetMapping
    public List<BloodUnit> getAllBloodUnits() {
        return bloodUnitService.getAllBloodUnits();
    }

    // Get blood unit by ID
    @GetMapping("/id/{id}")
    public BloodUnit getBloodUnitById(@PathVariable Long id) {
        return bloodUnitService.getBloodUnitById(id);
    }

    // Get stock by blood group
    @GetMapping("/stock")
    public Map<String, Long> getStockByBloodGroup() {
        return bloodUnitService.getStockByBloodGroup();
    }

    // Get blood units expiring within 7 days
    @GetMapping("/expiring")
    public List<BloodUnit> getExpiringBloodUnits() {
        return bloodUnitService.getExpiringBloodUnits();
    }

    // Get complete inventory summary
    @GetMapping("/summary")
    public Map<String, Object> getInventorySummary() {
        return bloodUnitService.getInventorySummary();
    }
}