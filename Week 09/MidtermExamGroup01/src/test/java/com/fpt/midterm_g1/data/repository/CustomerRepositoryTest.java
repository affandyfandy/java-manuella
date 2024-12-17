package com.fpt.midterm_g1.data.repository;

import com.fpt.midterm_g1.common.Status;
import com.fpt.midterm_g1.data.entity.Customer;
import com.fpt.midterm_g1.data.specification.CustomerSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer1;
    private Customer customer2;

    @BeforeEach
    void setUp() {
        customer1 = Customer.builder()
                .name("John Doe")
                .phoneNumber("123456789")
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        customer2 = Customer.builder()
                .name("Jane Smith")
                .phoneNumber("987654321")
                .status(Status.INACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        customerRepository.save(customer1);
        customerRepository.save(customer2);
    }

    @Test
    void testFindById() {
        Customer fetchedCustomer = customerRepository.findById(customer1.getId()).orElse(null);
        assertThat(fetchedCustomer).isNotNull();
        assertThat(fetchedCustomer.getName()).isEqualTo("John Doe");
    }

    @Test
    void testFindAll() {
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void testAdd() {
        Customer newCustomer = Customer.builder()
                .name("Alice Brown")
                .phoneNumber("555666777")
                .status(Status.ACTIVE)
                .createdTime(new Timestamp(System.currentTimeMillis()))
                .updatedTime(new Timestamp(System.currentTimeMillis()))
                .build();

        Customer savedCustomer = customerRepository.save(newCustomer);
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getName()).isEqualTo("Alice Brown");
    }

    @Test
    void testEdit() {
        customer1.setName("John Doe Updated");
        customerRepository.save(customer1);

        Customer updatedCustomer = customerRepository.findById(customer1.getId()).orElse(null);
        assertThat(updatedCustomer).isNotNull();
        assertThat(updatedCustomer.getName()).isEqualTo("John Doe Updated");
    }

    @Test
    void testActivate() {
        customer2.setStatus(Status.ACTIVE);
        customerRepository.save(customer2);

        Customer updatedCustomer = customerRepository.findById(customer2.getId()).orElse(null);
        assertThat(updatedCustomer).isNotNull();
        assertThat(updatedCustomer.getStatus()).isEqualTo(Status.ACTIVE);
    }

    @Test
    void testDeactivate() {
        customer1.setStatus(Status.INACTIVE);
        customerRepository.save(customer1);

        Customer updatedCustomer = customerRepository.findById(customer1.getId()).orElse(null);
        assertThat(updatedCustomer).isNotNull();
        assertThat(updatedCustomer.getStatus()).isEqualTo(Status.INACTIVE);
    }

    @Test
    void testSearchBySpec() {
        Specification<Customer> spec = new CustomerSpecification("Jane");
        List<Customer> customers = customerRepository.findAll(spec);
        assertThat(customers).isNotEmpty();
        assertThat(customers.get(0).getName()).isEqualTo("Jane Smith");
    }
}
