package com.lecture10.Assignment01.mapper;

import com.lecture10.Assignment01.dto.EmployeeDTO;
import com.lecture10.Assignment01.data.models.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDTO toEmployeeDTO(Employee employee);

    Employee toEmployee(EmployeeDTO employeeDTO);
}
