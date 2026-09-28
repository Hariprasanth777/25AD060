package com.bloodbank.controller;

import com.bloodbank.entity.IssueRecord;
import com.bloodbank.service.IssueRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueRecordController {

    private final IssueRecordService issueRecordService;

    public IssueRecordController(IssueRecordService issueRecordService) {
        this.issueRecordService = issueRecordService;
    }

    @PostMapping
    public IssueRecord addIssueRecord(@RequestBody IssueRecord issueRecord) {
        return issueRecordService.addIssueRecord(issueRecord);
    }

    @GetMapping
    public List<IssueRecord> getAllIssueRecords() {
        return issueRecordService.getAllIssueRecords();
    }

    @GetMapping("/{id}")
    public IssueRecord getIssueRecordById(@PathVariable Long id) {
        return issueRecordService.getIssueRecordById(id);
    }
}