package com.week6.Assignment01.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class TitleId implements Serializable {
    private int empNo;
    private String title;
    private Date fromDate;

    public TitleId(){

    }

    public TitleId(int empNo, String title, Date fromDate){
        this.empNo = empNo;
        this.title = title;
        this.fromDate = fromDate;
    }
}
