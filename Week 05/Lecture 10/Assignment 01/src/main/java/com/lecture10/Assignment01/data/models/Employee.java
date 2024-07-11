package com.lecture10.Assignment01.data.models;

import com.lecture10.Assignment01.util.DateUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import org.hibernate.annotations.GenericGenerator;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    private String name;
    private LocalDate dob;
    private String address;
    private String department;
    private String email;
    private String phone;

    public Employee(){
        this.id = UUID.randomUUID();
    }

    public Employee(String name, LocalDate dob, String address, String department, String email, String phone){
        this.id = UUID.randomUUID();
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.department = department;
        this.email = email;
        this.phone = phone;
    }

    public Employee(UUID id, String name, LocalDate dob, String address, String department, String email, String phone){
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.department = department;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return String.join(",", id.toString(), name, DateUtil.formatDate(dob), address, department);
    }
}
