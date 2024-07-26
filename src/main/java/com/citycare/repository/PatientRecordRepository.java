package com.citycare.repository;

import com.citycare.entity.PatientRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRecordRepository extends JpaRepository<PatientRecord, Long> {
    List<PatientRecord> findByMedicalHistory(String medicalHistory);
}
