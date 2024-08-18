package com.week6.Assignment01.service;

import com.week6.Assignment01.model.Employee;
import com.week6.Assignment01.specifications.EmployeeSpecification;
import com.week6.Assignment01.specifications.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecificationsBuilder {
    private final List<SearchCriteria> params;

    public EmployeeSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public EmployeeSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Employee> build() {
        if (params.isEmpty()) {
            return null;
        }

        Specification<Employee> result = new EmployeeSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(new EmployeeSpecification(params.get(i)));
        }

        return result;
    }
}