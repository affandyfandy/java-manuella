package com.week6.Assignment01.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class SalaryId implements Serializable {
    private int empNo;
    private Date fromDate;

    public SalaryId(){

    }
    public SalaryId(int empNo, Date fromDate){
        this.empNo = empNo;
        this.fromDate = fromDate;
    }
}
