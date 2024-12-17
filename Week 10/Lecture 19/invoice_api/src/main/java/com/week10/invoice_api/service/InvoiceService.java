package com.week10.invoice_api.service;

import com.week10.invoice_api.exception.ResourceNotFoundException;
import com.week10.invoice_api.model.Invoice;
import com.week10.invoice_api.model.Product;
import com.week10.invoice_api.repository.InvoiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final ProductService productService;

    public List<Invoice> getAllInvoices(){
        return invoiceRepository.findAll();
    }

    public Invoice getInvoiceById(Long id){
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);
        if (invoiceOptional.isEmpty()){
            throw new ResourceNotFoundException("Invoice not found for this id : " + id);
        }
        return invoiceOptional.get();
    }

    public Invoice saveInvoice(Invoice invoice){
        return invoiceRepository.save(invoice);
    }

    public Invoice updateInvoice(Long id, Invoice invoice){
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);
        if (invoiceOptional.isEmpty()){
            throw new ResourceNotFoundException("Invoice not found for this id : " + id);
        } else{
            Invoice saveInvoice = invoiceOptional.get();
            saveInvoice.setDate(invoice.getDate());
            saveInvoice.setProductId(invoice.getProductId());
            return invoiceRepository.save(saveInvoice);
        }
    }

    public void deleteInvoice(Long id){
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);
        if (invoiceOptional.isEmpty()){
            throw new ResourceNotFoundException("Invoice not found for this id : " + id);
        } else{
            invoiceRepository.delete(invoiceOptional.get());
        }
    }

    public Product getProductsForInvoice(Long id){
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);
        if (invoiceOptional.isEmpty()){
            throw new ResourceNotFoundException("Invoice not found for this id : " + id);
        }
        Invoice invoice = invoiceOptional.get();
        return productService.getProductById(invoice.getProductId());
    }

}
