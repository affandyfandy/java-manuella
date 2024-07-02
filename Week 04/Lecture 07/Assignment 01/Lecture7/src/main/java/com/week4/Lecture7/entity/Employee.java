package com.week4.Lecture7.entity;

import com.week4.Lecture7.EmployeeWork;

public class Employee {
    private int id;
    private String name;
    private int age;
    private EmployeeWork employeeWork;

    public Employee(){};

    public Employee(int id, String name, int age, EmployeeWork employeeWork){
        this.id = id;
        this.name = name;
        this.age = age;
        this.employeeWork = employeeWork;
    }

    public void working(){
        System.out.println("My name is: " + name);
        employeeWork.work();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public EmployeeWork getEmployeeWork() {
        return employeeWork;
    }

    public void setEmployeeWork(EmployeeWork employeeWork) {
        this.employeeWork = employeeWork;
    }
}
