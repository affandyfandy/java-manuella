package com.lecture8.EmployeeCRUD.dao;

import com.lecture8.EmployeeCRUD.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class EmployeeDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Employee> findAll() {
        String sql = "SELECT * FROM employees";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employee.class));
    }

    public Employee findById(String id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Employee.class));
    }

    public List<Employee> findByDepartment(String department) {
        String sql = "SELECT * FROM employees WHERE department = ?";
        return jdbcTemplate.query(sql, new Object[]{department}, new BeanPropertyRowMapper<>(Employee.class));
    }

    @Transactional
    public int save(Employee employee) {
        String sql = "INSERT INTO employees (id, name, dob, address, department) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, employee.getId(), employee.getName(), employee.getDob(), employee.getAddress(), employee.getDepartment());
    }

    @Transactional
    public int update(Employee employee) {
        String sql = "UPDATE employees SET name = ?, dob = ?, address = ?, department = ? WHERE id = ?";
        return jdbcTemplate.update(sql, employee.getName(), employee.getDob(), employee.getAddress(), employee.getDepartment(), employee.getId());
    }

    @Transactional
    public int deleteById(String id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Transactional
    public int saveAll(List<Employee> employees) {
        for (Employee employee : employees) {
            save(employee);
        }
        return employees.size();
    }
}
