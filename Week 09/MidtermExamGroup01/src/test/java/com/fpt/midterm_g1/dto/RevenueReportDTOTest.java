package com.fpt.midterm_g1.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RevenueReportDTOTest {

    @Test
    public void testRevenueReportDTO() {
        RevenueReportDTO dto = new RevenueReportDTO();
        dto.setDate(LocalDate.now());
        dto.setRevenue(new BigDecimal("5000.00"));

        assertEquals(dto.getDate(), LocalDate.now());
        assertEquals(dto.getRevenue(), new BigDecimal("5000.00"));
    }
}