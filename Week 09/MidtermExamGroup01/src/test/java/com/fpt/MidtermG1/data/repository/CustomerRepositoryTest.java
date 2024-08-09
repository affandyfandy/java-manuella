//package com.fpt.MidtermG1.data.repository;
//
//import com.fpt.MidtermG1.common.Status;
//import com.fpt.MidtermG1.data.entity.Customer;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.assertj.core.api.Assertions.*;
//
//import java.sql.Timestamp;
//
//@DataJpaTest
//@ActiveProfiles("test")
//class CustomerRepositoryTest {
//    @Autowired
//    private CustomerRepository customerRepository;

//    @Test
//    void testFindById() {
//        Customer customer = new Customer();
//        customer.setId("123");
//        customer.setName("John Doe");
//        customer.setPhoneNumber("+1234567890");
//        customer.setStatus(Status.ACTIVE);
//        customer.setCreatedTime(new Timestamp(System.currentTimeMillis()));
//        customer.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
//        customerRepository.save(customer);
//
//        Customer foundCustomer = customerRepository.findById(customer.getId()).orElse(null);
//
//        assertThat(foundCustomer).isNotNull();
//        assertThat(foundCustomer.getId()).isEqualTo(customer.getId());
//        assertThat(foundCustomer.getName()).isEqualTo(customer.getName());
//        assertThat(foundCustomer.getPhoneNumber()).isEqualTo(customer.getPhoneNumber());
//        assertThat(foundCustomer.getStatus()).isEqualTo(customer.getStatus());
//    }

//    @Test
//    void testFindAll() {
//        Customer customer = new Customer();
//        customer.setId("123");
//        customer.setName("John Doe");
//        customer.setPhoneNumber("+1234567890");
//        customer.setStatus(Status.ACTIVE);
//        customer.setCreatedTime(new Timestamp(System.currentTimeMillis()));
//        customer.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
//        customerRepository.save(customer);
//
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<Customer> customerPage = customerRepository.findAll(pageable);
//
//        assertThat(customerPage).isNotEmpty();
//        assertThat(customerPage.getTotalElements()).isEqualTo(1);
//        assertThat(customerPage.getContent().get(0).getId()).isEqualTo(customer.getId());
//    }
//}
