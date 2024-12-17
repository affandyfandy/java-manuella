package com.week10.invoice_api.repository;

import com.week10.invoice_api.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
