package com.bloodbank.service;

import com.bloodbank.entity.BloodUnit;
import com.bloodbank.entity.IssueRecord;
import com.bloodbank.repository.BloodUnitRepository;
import com.bloodbank.repository.IssueRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class IssueRecordService {

    private final IssueRecordRepository issueRecordRepository;
    private final BloodUnitRepository bloodUnitRepository;

    public IssueRecordService(
            IssueRecordRepository issueRecordRepository,
            BloodUnitRepository bloodUnitRepository) {

        this.issueRecordRepository = issueRecordRepository;
        this.bloodUnitRepository = bloodUnitRepository;
    }

    public IssueRecord addIssueRecord(IssueRecord issueRecord) {

        Long unitId = issueRecord.getBloodUnit().getUnitId();

        BloodUnit bloodUnit = bloodUnitRepository
                .findById(unitId)
                .orElse(null);

        if (bloodUnit == null) {
            throw new RuntimeException("Blood unit not found");
        }

        if (!"AVAILABLE".equalsIgnoreCase(bloodUnit.getStatus())) {
            throw new RuntimeException(
                    "Blood unit is not available for issue"
            );
        }

        bloodUnit.setStatus("ISSUED");

        bloodUnitRepository.save(bloodUnit);

        issueRecord.setBloodUnit(bloodUnit);

        return issueRecordRepository.save(issueRecord);
    }

    public List<IssueRecord> getAllIssueRecords() {
        return issueRecordRepository.findAll();
    }

    public IssueRecord getIssueRecordById(Long id) {
        return issueRecordRepository.findById(id).orElse(null);
    }
}