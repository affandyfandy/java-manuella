package com.week6.Assignment01.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "salaries")
@IdClass(SalaryId.class)
public class Salary {
    @Id
    @Column(name = "emp_no")
    private int empNo;

    @Column(name = "salary", nullable = false)
    private int salary;

    @Id
    @Temporal(TemporalType.DATE)
    @Column(name = "from_date")
    private Date fromDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "to_date")
    private Date toDate;

    @ManyToOne
    @JoinColumn(name = "emp_no", insertable = false, updatable = false)
    private Employee employee;
}
