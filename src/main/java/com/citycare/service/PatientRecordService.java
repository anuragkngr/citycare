package com.citycare.service;

import com.citycare.entity.PatientRecord;
import com.citycare.repository.PatientRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@EnableAsync
@EnableCaching
@Service
public class PatientRecordService {
    private static final Logger logger = LoggerFactory.getLogger(PatientRecordService.class);
    private static final String TOPIC = "patient_records";
    @Autowired
    private PatientRecordRepository repository;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

//    @Async
    @Cacheable(value = "PatientRecord")
    public PatientRecord create(PatientRecord record) {
        logger.info("PatientRecordService saveOrUpdate record: {}", record);
        PatientRecord savedRecord = repository.save(record);
        kafkaTemplate.send(TOPIC, savedRecord);
        return savedRecord;
    }

//    @Async
    @Cacheable(value = "PatientRecord", key = "#id")
    public PatientRecord getById(Long id) {
        logger.info("PatientRecordService getById id: {}", id);
        Optional<PatientRecord> patientRecord = repository.findById(id);
        return patientRecord.orElse(null);
    }

//    @Async
    @CachePut(value = "PatientRecord", key = "#patientRecord.id")
    public PatientRecord update(PatientRecord patientRecord) {
        logger.info("PatientRecordService updatePatientRecord record: {}", patientRecord);
        PatientRecord savedRecord = repository.save(patientRecord);
        kafkaTemplate.send(TOPIC, savedRecord);
        return savedRecord;
    }

//    @Async
    @CachePut(value = "PatientRecord", key = "#medicalHistory")
    public List<PatientRecord> getPatientRecordsByMedicalHistory(String medicalHistory) {
        logger.info("PatientRecordService getPatientRecordsByMedicalHistory record: {}", medicalHistory);
        return repository.findByMedicalHistory(medicalHistory);
    }

    //    @Async
    public List<PatientRecord> getAll() {
        logger.info("PatientRecordService getAll record");
        return repository.findAll();
    }
}
