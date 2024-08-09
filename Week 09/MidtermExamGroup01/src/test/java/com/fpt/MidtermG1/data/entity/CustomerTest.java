package com.fpt.MidtermG1.data.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fpt.MidtermG1.common.Status;
import com.fpt.MidtermG1.dto.CustomerDTO;

class CustomerTest {
    private Customer customer;
    @BeforeEach
    public void setUp() {
        customer = Customer.builder()
                .id("123")
                .name("John Doe")
                .phoneNumber("+1234567890")
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        CustomerDTO customerDTO = CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phoneNumber(customer.getPhoneNumber())
                .status(customer.getStatus())
                .createdTime(customer.getCreatedTime())
                .updatedTime(customer.getUpdatedTime())
                .build();
    }

    @Test
    public void testCustomerToDTO() {
        CustomerDTO dto = customer.toDTO();
        assertEquals(customer.getId(), dto.getId());
        assertEquals(customer.getName(), dto.getName());
        assertEquals(customer.getPhoneNumber(), dto.getPhoneNumber());
        assertEquals(customer.getStatus(), dto.getStatus());
        assertEquals(customer.getCreatedTime(), dto.getCreatedTime());
        assertEquals(customer.getUpdatedTime(), dto.getUpdatedTime());
    }

    @Test
    public void testCustomerPrePersist() {
        customer.onCreate();
        assertNotNull(customer.getCreatedTime());
        assertNotNull(customer.getUpdatedTime());
    }

    @Test
    public void testCustomerPreUpdate() {
        customer.preUpdate();
        assertNotNull(customer.getUpdatedTime());
    }
}
