package com.fpt.midterm_g1.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.fpt.midterm_g1.common.Status;
import com.fpt.midterm_g1.data.entity.Customer;
import com.fpt.midterm_g1.data.repository.CustomerRepository;
import com.fpt.midterm_g1.data.specification.CustomerSpecification;
import com.fpt.midterm_g1.dto.CustomerDTO;
import com.fpt.midterm_g1.exception.ResourceNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CustomerServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CustomerServiceImplTest {
    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCustomerList() {
        Customer customer = new Customer();
        customer.setId("123e4567-e89b-12d3-a456-426614174000");
        customer.setName("John Doe");

        Page<Customer> customersPage = new PageImpl<>(Collections.singletonList(customer));
        when(customerRepository.findAll(any(Pageable.class))).thenReturn(customersPage);

        Page<CustomerDTO> result = customerService.getCustomerList(Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("John Doe", result.getContent().get(0).getName());
    }

    @Test
    void testSearchCustomers() {
        Customer customer = new Customer();
        customer.setId("123e4567-e89b-12d3-a456-426614174000");
        customer.setName("John Doe");

        Page<Customer> customersPage = new PageImpl<>(Collections.singletonList(customer));
        when(customerRepository.findAll(any(CustomerSpecification.class), any(Pageable.class))).thenReturn(customersPage);

        Page<CustomerDTO> result = customerService.searchCustomers("John", Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("John Doe", result.getContent().get(0).getName());
    }

    @Test
    void testGetCustomerById() {
        Customer customer = new Customer();
        customer.setId("123e4567-e89b-12d3-a456-426614174000");
        customer.setName("John Doe");

        when(customerRepository.findById("123e4567-e89b-12d3-a456-426614174000")).thenReturn(Optional.of(customer));

        Optional<CustomerDTO> result = customerService.getCusromerById("123e4567-e89b-12d3-a456-426614174000");

        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
    }

    @Test
    void testAddCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId("123e4567-e89b-12d3-a456-426614174000");
        customerDTO.setName("John Doe");

        Customer customer = new Customer();
        customer.setId("123e4567-e89b-12d3-a456-426614174000");
        customer.setName("John Doe");

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO result = customerService.addCustomer(customerDTO);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void testEditCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("John Updated");

        Customer existingCustomer = new Customer();
        existingCustomer.setId("123e4567-e89b-12d3-a456-426614174000");
        existingCustomer.setName("John Doe");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setId("123e4567-e89b-12d3-a456-426614174000");
        updatedCustomer.setName("John Updated");

        when(customerRepository.findById("123e4567-e89b-12d3-a456-426614174000")).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        CustomerDTO result = customerService.editCustomer("123e4567-e89b-12d3-a456-426614174000", customerDTO);

        assertNotNull(result);
        assertEquals("John Updated", result.getName());
    }

    @Test
    void testActivateCustomer() {
        Customer customer = new Customer();
        customer.setId("123e4567-e89b-12d3-a456-426614174000");
        customer.setStatus(Status.INACTIVE);

        when(customerRepository.findById("123e4567-e89b-12d3-a456-426614174000")).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO result = customerService.activateCustomer("123e4567-e89b-12d3-a456-426614174000");

        assertNotNull(result);
        assertEquals(Status.ACTIVE, result.getStatus());
    }

    @Test
    void testDeactivateCustomer() {
        Customer customer = new Customer();
        customer.setId("123e4567-e89b-12d3-a456-426614174000");
        customer.setStatus(Status.ACTIVE);

        when(customerRepository.findById("123e4567-e89b-12d3-a456-426614174000")).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO result = customerService.deactivateCustomer("123e4567-e89b-12d3-a456-426614174000");

        assertNotNull(result);
        assertEquals(Status.INACTIVE, result.getStatus());
    }

    @Test
    void testActivateCustomerNotFound() {
        when(customerRepository.findById("123e4567-e89b-12d3-a456-426614174000")).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            customerService.activateCustomer("123e4567-e89b-12d3-a456-426614174000");
        });

        assertEquals("Customer not found with id: 123e4567-e89b-12d3-a456-426614174000", exception.getMessage());
    }

    @Test
    void testDeactivateCustomerNotFound() {
        when(customerRepository.findById("123e4567-e89b-12d3-a456-426614174000")).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            customerService.deactivateCustomer("123e4567-e89b-12d3-a456-426614174000");
        });

        assertEquals("Customer not found with id: 123e4567-e89b-12d3-a456-426614174000", exception.getMessage());
    }
}
