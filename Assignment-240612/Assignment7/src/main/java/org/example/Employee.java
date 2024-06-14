package org.example;

import java.time.LocalDate;

public class Employee {
    private String id;
    private String name;
    private LocalDate dob;

    private String address;

    private String department;

    public Employee(String id, String name, LocalDate dob, String address, String department){
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.department = department;
    }

    @Override
    public String toString(){
        return String.join(",", id, name, DateUtils.formatDate(dob), address, department);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
