package com.week5.Assignment03.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;

@Entity
@Table(name = "employee")
@Getter
@Setter
public class Employee {
    @Id
    @Column(name="id")
    private String id;

    private String name;
    private LocalDate dateOfBirth;
    private String address;
    private String department;
    private Double salary;
    private String formattedDateOfBirth;
}
