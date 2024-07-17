package com.week6.Assignment01.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DeptEmpId implements Serializable {
    private int empNo;
    private String deptNo;

    public DeptEmpId() {
    }

    public DeptEmpId(int empNo, String deptNo){
        this.empNo = empNo;
        this.deptNo = deptNo;
    }
}
