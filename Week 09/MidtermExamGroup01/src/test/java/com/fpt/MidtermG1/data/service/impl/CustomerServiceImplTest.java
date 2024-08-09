package com.fpt.MidtermG1.data.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Optional;

import com.fpt.MidtermG1.data.repository.CustomerRepository;
import com.fpt.MidtermG1.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.fpt.MidtermG1.common.Status;
import com.fpt.MidtermG1.data.entity.Customer;
import com.fpt.MidtermG1.data.specification.CustomerSpecification;
import com.fpt.MidtermG1.dto.CustomerDTO;

class CustomerServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;
    private CustomerDTO customerDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = Customer.builder()
                .id("123")
                .name("John Doe")
                .phoneNumber("+1234567890")
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        customerDTO = CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phoneNumber(customer.getPhoneNumber())
                .status(customer.getStatus())
                .createdTime(customer.getCreatedTime())
                .updatedTime(customer.getUpdatedTime())
                .build();
    }

    @Test
    void testGetCustomerList() {
        Page<Customer> customerPage = new PageImpl<>(Collections.singletonList(customer));
        when(customerRepository.findAll(any(Pageable.class))).thenReturn(customerPage);

        Page<CustomerDTO> result = customerService.getCustomerList(Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
        assertEquals(customerDTO, result.getContent().get(0));
    }

    @Test
    void testSearchCustomers() {
        Page<Customer> customerPage = new PageImpl<>(Collections.singletonList(customer));
        when(customerRepository.findAll(any(CustomerSpecification.class), any(Pageable.class))).thenReturn(customerPage);

        Page<CustomerDTO> result = customerService.searchCustomers("John", Pageable.unpaged());

        assertEquals(1, result.getTotalElements());
        assertEquals(customerDTO, result.getContent().get(0));
    }

    @Test
    void testGetCustomerById() {
        when(customerRepository.findById(anyString())).thenReturn(Optional.of(customer));

        Optional<CustomerDTO> result = customerService.getCusromerById(customer.getId());

        assertEquals(Optional.of(customerDTO), result);
    }

    @Test
    void testAddCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO result = customerService.addCustomer(customerDTO);

        assertEquals(customerDTO, result);
    }

    @Test
    void testEditCustomer() {
        Customer updatedCustomer = Customer.builder().name("Jane Doe").build();
        when(customerRepository.findById(anyString())).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        CustomerDTO updatedCustomerDTO = CustomerDTO.builder().name("Jane Doe").build();
        CustomerDTO result = customerService.editCustomer(customer.getId(), updatedCustomerDTO);

        assertEquals(updatedCustomerDTO, result);
    }

//    @Test
//    void testActivateCustomer() {
//        when(customerRepository.findById(anyString())).thenReturn(Optional.of(customer));
//        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
//
//        CustomerDTO result = customerService.activateCustomer(customer.getId());
//
//        assertEquals(customerDTO, result);
//        verify(customerRepository, times(1)).save(any(Customer.class));
//    }
//
//    @Test
//    void testDeactivateCustomer() {
//        Customer activeCustomer = Customer.builder().status(Status.ACTIVE).build();
//        when(customerRepository.findById(anyString())).thenReturn(Optional.of(activeCustomer));
//        when(customerRepository.save(any(Customer.class))).thenReturn(activeCustomer);
//
//        CustomerDTO result = customerService.deactivateCustomer(activeCustomer.getId());
//
//        assertEquals(customerDTO, result);
//        verify(customerRepository, times(1)).save(any(Customer.class));
//    }

//    @Test
//    void testEditCustomerNotFound() {
//        when(customerRepository.findById(anyString())).thenReturn(Optional.empty());
//
//        assertThrows(RuntimeException.class, () -> {
//            customerService.editCustomer(customer.getId(), customerDTO);
//        });
//    }
//
//    @Test
//    void testActivateCustomerNotFound() {
//        when(customerRepository.findById(anyString())).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> {
//            customerService.activateCustomer(customer.getId());
//        });
//    }
//
//    @Test
//    void testDeactivateCustomerNotFound() {
//        when(customerRepository.findById(anyString())).thenReturn(Optional.empty());
//
//        assertThrows(ResourceNotFoundException.class, () -> {
//            customerService.deactivateCustomer(customer.getId());
//        });
//    }
}
