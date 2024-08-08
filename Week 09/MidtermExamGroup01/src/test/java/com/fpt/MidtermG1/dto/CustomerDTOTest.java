package com.fpt.MidtermG1.dto;

import com.fpt.MidtermG1.data.entity.Customer;
import com.fpt.MidtermG1.common.Status;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerDTOTest {

    @Test
    public void testToEntity() {
        CustomerDTO dto = CustomerDTO.builder()
                .id("123")
                .name("John Doe")
                .phoneNumber("+1234567890")
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        Customer customer = dto.toEntity();

        assertEquals(dto.getId(), customer.getId());
        assertEquals(dto.getName(), customer.getName());
        assertEquals(dto.getPhoneNumber(), customer.getPhoneNumber());
        assertEquals(dto.getStatus(), customer.getStatus());
        assertEquals(dto.getCreatedTime(), customer.getCreatedTime());
        assertEquals(dto.getUpdatedTime(), customer.getUpdatedTime());
    }
}
