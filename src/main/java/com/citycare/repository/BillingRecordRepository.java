package com.citycare.repository;

import com.citycare.entity.BillingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingRecordRepository extends JpaRepository<BillingRecord, Long> {
    void deleteByPatientId(Long id);

}
