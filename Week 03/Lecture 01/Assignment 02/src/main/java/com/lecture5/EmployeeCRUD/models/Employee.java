package com.lecture5.EmployeeCRUD.models;

import com.lecture5.EmployeeCRUD.utils.DateUtils;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String name;
    private LocalDate dob;

    private String address;

    private String department;

    public Employee(){

    }

    public Employee(String id, String name, LocalDate dob, String address, String department){
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.department = department;
    }

    @Override
    public String toString() {
        return String.join(",", id.toString(), name, DateUtils.formatDate(dob), address, department);
    }
}