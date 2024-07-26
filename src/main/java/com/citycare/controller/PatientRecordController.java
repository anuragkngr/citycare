package com.citycare.controller;

import com.citycare.entity.PatientRecord;
import com.citycare.exception.ResourceNotFoundException;
import com.citycare.service.PatientRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/patient-records")
public class PatientRecordController {
    private static final Logger logger = LoggerFactory.getLogger(PatientRecordController.class);

    @Autowired
    private PatientRecordService service;

    @PostMapping
    public PatientRecord create(@RequestBody PatientRecord record) {
        logger.info("PatientRecordController createRecord Received request to update patient: {}", record);
        try{
            PatientRecord savedRecord = service.create(record);
            logger.info("PatientRecordController createRecord Returning patient data : {}", savedRecord);
            return savedRecord;
        } catch (Exception ex) {
            logger.error("An error occurred while creating new patient record: {}", record, ex);
        }
        return null;
    }

    @PutMapping
    public PatientRecord update(@RequestBody PatientRecord record) {
        logger.info("PatientRecordController updateRecord Received request to update patient: {}", record);
        try{
            PatientRecord savedRecord = service.update(record);
            logger.info("PatientRecordController updateRecord Returning patient data : {}", savedRecord);
            return savedRecord;
        } catch (ResourceNotFoundException ex) {
            logger.error("Patient not found with ID: {}", record, ex);
        } catch (Exception ex) {
            logger.error("An error occurred while fetching patient with ID: {}", record, ex);
        }
        return null;//ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public PatientRecord getById(@PathVariable Long id) {
        logger.info("PatientRecordController getRecordById Received request to fetch patient with ID: {}", id);
        try {
            PatientRecord patient = service.getById(id);
            logger.info("Returning patient data for ID: {}", id);
            logger.info("PatientRecordController getRecordById Returning patient data for ID is : {}", patient);
            return patient;//.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (ResourceNotFoundException ex) {
            logger.error("Patient not found with ID: {}", id, ex);
            // Let the GlobalExceptionHandler handle this
        } catch (Exception ex) {
            logger.error("An error occurred while fetching patient with ID: {}", id, ex);
            // Let the GlobalExceptionHandler handle this
        }
        return null;
    }

    @GetMapping("/all")
    public List<PatientRecord> getAll() {
        logger.info("PatientRecordController getAll Received request to fetch all patient");
        try {
            List<PatientRecord> patientList = service.getAll();
            logger.info("PatientRecordController insuranceRecord records {}", patientList);
            return patientList;
//            return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (ResourceNotFoundException ex) {
            logger.error("Patient not found ", ex);
            // Let the GlobalExceptionHandler handle this
        } catch (Exception ex) {
            logger.error("An error occurred while fetching patient", ex);
            // Let the GlobalExceptionHandler handle this
        }
        return Collections.emptyList();
    }

    @GetMapping("/medicalHistory/{medicalHistory}")
    public ResponseEntity<List<PatientRecord>> getByMedicalHistory(@PathVariable String medicalHistory) {
        logger.info("PatientRecordController getRecordByMedicalHistory Received request to fetch patient with ID: {}", medicalHistory);
        try {
            ResponseEntity<List<PatientRecord>> patientRecordList = (ResponseEntity<List<PatientRecord>>) service.getPatientRecordsByMedicalHistory(medicalHistory);
            logger.info("Returning patient data for medicalHistory: {}", medicalHistory);
            logger.info("PatientRecordController getRecordById Returning patient data for medicalHistory : {}", medicalHistory);
            return patientRecordList;//ResponseEntity.ok(patientRecordList)
        } catch (ResourceNotFoundException ex) {
            logger.error("Patient not found with medicalHistory: {}", medicalHistory, ex);
            // Let the GlobalExceptionHandler handle this
        } catch (Exception ex) {
            logger.error("An error occurred while fetching patient with medicalHistory: {}", medicalHistory, ex);
            // Let the GlobalExceptionHandler handle this
        }
        return ResponseEntity.notFound().build();
    }
}
