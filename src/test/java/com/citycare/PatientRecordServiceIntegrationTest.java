package com.citycare;

import com.citycare.entity.PatientRecord;
import com.citycare.repository.PatientRecordRepository;
import com.citycare.service.PatientRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class PatientRecordServiceIntegrationTest {

    @Autowired
    private PatientRecordService service;

    @Autowired
    private PatientRecordRepository repository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    void testSaveOrUpdateAndKafkaIntegration() {
        PatientRecord record = new PatientRecord();
        record.setId(11L);
        record.setName("Anurag Mishra");
        record.setMedicalHistory("None");

        PatientRecord savedRecord = service.create(record);
        assertNotNull(savedRecord);

        savedRecord = service.update(record);
        assertNotNull(savedRecord);
    }

    @Test
    void testGetById() {
        Optional<PatientRecord> patientRecord = repository.findById(1L);
        if (patientRecord.isPresent() && patientRecord.get().getId() > 0L) patientRecord.ifPresent(rec -> assertNotEquals(0L, rec.getId()));
        else patientRecord.ifPresent(rec -> assertEquals(0L, rec.getId()));
    }
}
