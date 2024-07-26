package com.citycare.repository;

import com.citycare.entity.InsuranceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRecordRepository extends JpaRepository<InsuranceRecord, Long> {
    void deleteByPatientId(Long id);
}
