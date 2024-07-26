package com.citycare.service;

import com.citycare.entity.InsuranceRecord;
import com.citycare.entity.PatientRecord;
import com.citycare.repository.InsuranceRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@EnableAsync
@EnableCaching
@Service
public class InsuranceRecordService {
    @Autowired
    private InsuranceRecordRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(InsuranceRecordService.class);

    //    @Async
    @Cacheable(value = "InsuranceRecord", key = "#record.id")
    public InsuranceRecord save(InsuranceRecord record) {
        logger.info("InsuranceRecordService save record: {}", record);
        return repository.save(record);
    }

    @Async
    @KafkaListener(topics = "patient_records", groupId = "group_id")
    @CachePut(value = "InsuranceRecord", key = "#record.id")
    public void consumePatientRecord(PatientRecord record) {
        logger.info("InsuranceRecordService consumePatientRecord record: {}", record);
        InsuranceRecord insuranceRecord = new InsuranceRecord();
        insuranceRecord.setPatientId(record.getId());
        insuranceRecord.setInsuranceProvider("Default Provider"); // Example provider
        insuranceRecord.setPolicyNumber("POLICY123"); // Example policy number
        repository.save(insuranceRecord);
    }

//    @Async
    @Cacheable(value = "InsuranceRecord", key = "#id")
    public Optional<InsuranceRecord> getById(Long id) {
        logger.info("InsuranceRecordService getById record: {}", id);
        return repository.findById(id);
    }

    @Async
    @CacheEvict(value = "InsuranceRecord", key = "#patientId")
    public void delete(Long patientId) {
        logger.info("InsuranceRecordService delete record: patientId = {}", patientId);
        repository.deleteById(patientId);
    }

    //    @Async
    public List<InsuranceRecord> getAll() {
        logger.info("InsuranceRecordService getAll record");
        return repository.findAll();
    }

}
