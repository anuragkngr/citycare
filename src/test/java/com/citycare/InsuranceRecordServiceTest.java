package com.citycare;

import com.citycare.entity.InsuranceRecord;
import com.citycare.entity.PatientRecord;
import com.citycare.repository.InsuranceRecordRepository;
import com.citycare.service.InsuranceRecordService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

class InsuranceRecordServiceTest {

    @Mock
    private InsuranceRecordRepository repository;

    @InjectMocks
    private InsuranceRecordService service;

    public InsuranceRecordServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        InsuranceRecord record = new InsuranceRecord();
        record.setId(1L);
        record.setPatientId(1L);
        record.setInsuranceProvider("Default Provider");
        record.setPolicyNumber("POLICY123");

        when(repository.save(record)).thenReturn(record);

        InsuranceRecord savedRecord = service.save(record);

        assertEquals(1L, savedRecord.getId());
        assertEquals("Default Provider", savedRecord.getInsuranceProvider());
    }

    @Test
    void testConsumePatientRecord() {
        PatientRecord patientRecord = new PatientRecord();
        patientRecord.setId(1L);
        patientRecord.setName("Anurag Mishra");
        patientRecord.setMedicalHistory("None");

        InsuranceRecord insuranceRecord = new InsuranceRecord();
        insuranceRecord.setPatientId(patientRecord.getId());
        insuranceRecord.setInsuranceProvider("Default Provider");
        insuranceRecord.setPolicyNumber("POLICY123");

        when(repository.save(any(InsuranceRecord.class))).thenReturn(insuranceRecord);

        service.consumePatientRecord(patientRecord);

        verify(repository, times(1)).save(any(InsuranceRecord.class));
    }

    @Test
    void testGetById() {
        Optional<InsuranceRecord> insuranceRecord = repository.findById(1L);
        insuranceRecord.ifPresent(record -> assertNotEquals(0L, record.getId()));
    }
}
