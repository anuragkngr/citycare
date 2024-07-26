package com.citycare.controller;

import com.citycare.entity.InsuranceRecord;
import com.citycare.exception.ResourceNotFoundException;
import com.citycare.service.InsuranceRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/insurance-records")
public class InsuranceRecordController {
    private static final Logger logger = LoggerFactory.getLogger(InsuranceRecordController.class);

    @Autowired
    private InsuranceRecordService service;

    @PostMapping
    public ResponseEntity<InsuranceRecord> create(@RequestBody InsuranceRecord record) {
        logger.info("InsuranceRecordController createBillingRecord Received request record: {}", record);
        try{
            InsuranceRecord savedRecord = service.save(record);
            logger.info("InsuranceRecordController createBillingRecord Received Returning savedRecord: {}", savedRecord);
            return ResponseEntity.ok(savedRecord);
        } catch (Exception ex) {
            logger.error("An error occurred while creating insuranceRecord with ID: {}", record, ex);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsuranceRecord> getById(@PathVariable Long id) {
        logger.info("InsuranceRecordController getInsuranceRecordById Received request ID: {}", id);
        try {
            Optional<InsuranceRecord> insuranceRecord = service.getById(id);
            logger.info("Returning insuranceRecord data for ID: {}", id);
            logger.info("InsuranceRecordController getInsuranceRecordById Returning insuranceRecord data for ID is : {}", insuranceRecord);
            return insuranceRecord.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (ResourceNotFoundException ex) {
            logger.error("insuranceRecord not found with ID: {}", id, ex);
        } catch (Exception ex) {
            logger.error("An error occurred while fetching insuranceRecord with ID: {}", id, ex);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<InsuranceRecord>> getAll() {
        logger.info("insuranceRecord Received");
        try {
            ResponseEntity<List<InsuranceRecord>> insuranceRecordList = (ResponseEntity<List<InsuranceRecord>>) service.getAll().stream().toList();
            logger.info(" insuranceRecord getAll data {}", insuranceRecordList);
            return insuranceRecordList;
        } catch (ResourceNotFoundException ex) {
            logger.error("getAll not found", ex);
        } catch (Exception ex) {
            logger.error("An error occurred while fetching getAll", ex);
        }
        return ResponseEntity.notFound().build();
    }
}
