package com.citycare.controller;

import com.citycare.entity.BillingRecord;
import com.citycare.exception.ResourceNotFoundException;
import com.citycare.service.BillingRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/billing-records")
public class BillingRecordController {
    private static final Logger logger = LoggerFactory.getLogger(BillingRecordController.class);

    @Autowired
    private BillingRecordService service;

    @PostMapping
    public ResponseEntity<BillingRecord> create(@RequestBody BillingRecord record) {
        logger.info("BillingRecordController createBillingRecord Received request record: {}", record);
        try{
            BillingRecord savedRecord = service.save(record);
            logger.info("BillingRecordController createBillingRecord Returning savedRecord: {}", savedRecord);
            return ResponseEntity.ok(savedRecord);
        } catch (Exception ex) {
            logger.error("An error occurred while creating billingRecord with record: {}", record, ex);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillingRecord> getById(@PathVariable Long id) {
        logger.info("BillingRecordController getRecordById Received request ID: {}", id);
        try {
            Optional<BillingRecord> billingRecord = service.getById(id);
            logger.info("Returning billingRecord data for ID: {}", id);
            logger.info("BillingRecordController getBillingRecordById Returning billingRecord data for ID is : {}", billingRecord);
            return billingRecord.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (ResourceNotFoundException ex) {
            logger.error("billingRecord not found with ID: {}", id, ex);
        } catch (Exception ex) {
            logger.error("An error occurred while fetching billingRecord with ID: {}", id, ex);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<BillingRecord>> getAll() {
        logger.info("billingRecord getAll Received");
        try {
            ResponseEntity<List<BillingRecord>> billingRecordList = (ResponseEntity<List<BillingRecord>>) service.getAll().stream().toList();
            logger.info(" billingRecord getAll data {}", billingRecordList);
            return billingRecordList;
        } catch (ResourceNotFoundException ex) {
            logger.error("billingRecord not found", ex);
        } catch (Exception ex) {
            logger.error("An error occurred while fetching billingRecordList", ex);
        }
        return ResponseEntity.notFound().build();
    }
}