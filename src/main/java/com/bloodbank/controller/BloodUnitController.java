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

    @PostMapping
    public BloodUnit addBloodUnit(@RequestBody BloodUnit bloodUnit) {
        return bloodUnitService.addBloodUnit(bloodUnit);
    }

    @GetMapping
    public List<BloodUnit> getAllBloodUnits() {
        return bloodUnitService.getAllBloodUnits();
    }

    @GetMapping("/{id}")
    public BloodUnit getBloodUnitById(@PathVariable Long id) {
        return bloodUnitService.getBloodUnitById(id);
    }
    @GetMapping("/stock")
    public Map<String, Long> getStockByBloodGroup() {
        return bloodUnitService.getStockByBloodGroup();
    }
}