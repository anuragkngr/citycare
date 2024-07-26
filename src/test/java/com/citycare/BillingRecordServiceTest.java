package com.citycare;

import com.citycare.entity.BillingRecord;
import com.citycare.entity.PatientRecord;
import com.citycare.repository.BillingRecordRepository;
import com.citycare.service.BillingRecordService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

class BillingRecordServiceTest {

    @Mock
    private BillingRecordRepository repository;

    @InjectMocks
    private BillingRecordService service;

    public BillingRecordServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        BillingRecord record = new BillingRecord();
        record.setId(1L);
        record.setPatientId(1L);
        record.setAmount(100.0);

        when(repository.save(record)).thenReturn(record);

        BillingRecord savedRecord = service.save(record);

        assertEquals(1L, savedRecord.getId());
        assertEquals(100.0, savedRecord.getAmount());
    }

    @Test
    void testConsumePatientRecord() {
        PatientRecord patientRecord = new PatientRecord();
        patientRecord.setId(1L);
        patientRecord.setName("Anurag Mishra");
        patientRecord.setMedicalHistory("None");

        BillingRecord billingRecord = new BillingRecord();
        billingRecord.setPatientId(patientRecord.getId());
        billingRecord.setAmount(100.0);

        when(repository.save(any(BillingRecord.class))).thenReturn(billingRecord);

        service.consumePatientRecord(patientRecord);

        verify(repository, times(1)).save(any(BillingRecord.class));
    }

    @Test
    void testGetById() {
        Optional<BillingRecord> billingRecord = repository.findById(1L);
        billingRecord.ifPresent(record -> assertNotEquals(0L, record.getId()));
    }

}

