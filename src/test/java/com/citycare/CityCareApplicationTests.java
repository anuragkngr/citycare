package com.citycare;

import com.citycare.controller.BillingRecordController;
import com.citycare.controller.InsuranceRecordController;
import com.citycare.controller.PatientRecordController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CityCareApplicationTests {

    @Autowired
    private PatientRecordController patientRecordController;
    @Autowired
    private BillingRecordController billingRecordController;
    @Autowired
    private InsuranceRecordController insuranceRecordController;

    @Test
    void contextLoads() {
        String str = "Test";
        assertThat(str).isNotNull();
        assertThat(patientRecordController).isNotNull();
        assertThat(billingRecordController).isNotNull();
        assertThat(insuranceRecordController).isNotNull();
    }

}
