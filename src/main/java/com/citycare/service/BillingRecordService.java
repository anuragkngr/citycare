package com.citycare.service;

import com.citycare.entity.BillingRecord;
import com.citycare.entity.PatientRecord;
import com.citycare.repository.BillingRecordRepository;
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
public class BillingRecordService {
    @Autowired
    private BillingRecordRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(BillingRecordService.class);

    //    @Async
    @Cacheable(value = "BillingRecord", key = "#record.id")
    public BillingRecord save(BillingRecord record) {
        logger.info("BillingRecordService save record: {}", record);
        return repository.save(record);
    }

    @Async
    @KafkaListener(topics = "patient_records", groupId = "group_id")
    @CachePut(value = "BillingRecord", key = "#patientRecord.id")
    public void consumePatientRecord(PatientRecord patientRecord) {
        logger.info("BillingRecordService consumePatientRecord record: {}", patientRecord);
        BillingRecord billingRecord = new BillingRecord();
        billingRecord.setPatientId(patientRecord.getId());
        billingRecord.setAmount(100.0); // Example amount
        repository.save(billingRecord);
    }

//    @Async
    @Cacheable(value = "BillingRecord", key = "#id")
    public Optional<BillingRecord> getById(Long id) {
        logger.info("BillingRecordService getById record: {}", id);
        return repository.findById(id);
    }

//    @Async
    public List<BillingRecord> getAll() {
        logger.info("BillingRecordService getAll record");
        return repository.findAll();
    }

    @Async
    @CacheEvict(value = "BillingRecord", key = "#patientId")
    public void delete(Long patientId) {
        logger.info("BillingRecordService delete record: patientId = {}", patientId);
        repository.deleteByPatientId(patientId);
    }

}
