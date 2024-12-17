package com.fpt.midterm_g1.data.entity;

import com.fpt.midterm_g1.common.Status;
import com.fpt.midterm_g1.dto.CustomerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import java.sql.Timestamp;

class CustomerTest {
    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = Customer.builder()
                .id("123e4567-e89b-12d3-a456-426614174000")
                .name("John Doe")
                .phoneNumber("1234567890")
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    @Test
    void testCustomerEntity() {
        Assertions.assertThat(customer).isNotNull();
        Assertions.assertThat(customer.getId()).isEqualTo("123e4567-e89b-12d3-a456-426614174000");
        Assertions.assertThat(customer.getName()).isEqualTo("John Doe");
        Assertions.assertThat(customer.getPhoneNumber()).isEqualTo("1234567890");
        Assertions.assertThat(customer.getStatus()).isEqualTo(Status.ACTIVE);
        Assertions.assertThat(customer.getCreatedTime()).isNotNull();
        Assertions.assertThat(customer.getUpdatedTime()).isNotNull();
    }

    @Test
    void testCustomerDTOConversion() {
        CustomerDTO dto = customer.toDTO();
        Assertions.assertThat(dto).isNotNull();
        Assertions.assertThat(dto.getId()).isEqualTo(customer.getId());
        Assertions.assertThat(dto.getName()).isEqualTo(customer.getName());
        Assertions.assertThat(dto.getPhoneNumber()).isEqualTo(customer.getPhoneNumber());
        Assertions.assertThat(dto.getStatus()).isEqualTo(customer.getStatus());
        Assertions.assertThat(dto.getCreatedTime()).isEqualTo(customer.getCreatedTime());
        Assertions.assertThat(dto.getUpdatedTime()).isEqualTo(customer.getUpdatedTime());
    }

    @Test
    void testCustomerPrePersist() {
        Customer newCustomer = new Customer();
        newCustomer.onCreate();
        Assertions.assertThat(newCustomer.getCreatedTime()).isNotNull();
        Assertions.assertThat(newCustomer.getUpdatedTime()).isNotNull();
    }

    @Test
    void testCustomerPreUpdate() {
        Timestamp initialUpdatedTime = customer.getUpdatedTime();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        customer.preUpdate();
        Timestamp updatedTimeAfterUpdate = customer.getUpdatedTime();
        Assertions.assertThat(updatedTimeAfterUpdate)
                .isAfter(initialUpdatedTime);
    }
}
