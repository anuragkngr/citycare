package com.citycare;

import com.citycare.entity.PatientRecord;
import com.citycare.repository.PatientRecordRepository;
import com.citycare.service.PatientRecordService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class PatientRecordServiceTest {

    @Mock
    private PatientRecordRepository repository;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private PatientRecordService service;

    public PatientRecordServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        PatientRecord record = new PatientRecord();
        record.setId(1L);
        record.setName("Anurag Mishra");
        record.setMedicalHistory("None");

        when(repository.save(record)).thenReturn(record);

        PatientRecord savedRecord = service.create(record);

        assertEquals(1L, savedRecord.getId());
        verify(kafkaTemplate, times(1)).send(anyString(), any(PatientRecord.class));
    }

    @Test
    void testUpdate() {
        PatientRecord record = new PatientRecord();
        record.setId(1L);
        record.setName("Anurag Mishra");
        record.setMedicalHistory("None");

        when(repository.save(record)).thenReturn(record);

        PatientRecord savedRecord = service.update(record);

        assertEquals(1L, savedRecord.getId());
        verify(kafkaTemplate, times(1)).send(anyString(), any(PatientRecord.class));
    }

    @Test
    void testGetById() {
        PatientRecord record = new PatientRecord();
        record.setId(1L);
        record.setName("Anurag Mishra");
        record.setMedicalHistory("None");

        when(repository.findById(1L)).thenReturn(Optional.of(record));

        Optional<PatientRecord> retrievedRecord = service.getById(1L);

        assertTrue(retrievedRecord.isPresent());
        assertEquals("Anurag Mishra", retrievedRecord.get().getName());
    }
}
