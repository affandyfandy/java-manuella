package com.lecture8.EmployeeCRUD.dao;

import com.lecture8.EmployeeCRUD.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Employee.class));
        } catch (DataAccessException e) {
            throw new RuntimeException("Error fetching employee with id " + id, e);
        }
    }


    public List<Employee> findByDepartment(String department) {
        String sql = "SELECT * FROM employees WHERE department = ?";
        return jdbcTemplate.query(sql, new Object[]{department}, new BeanPropertyRowMapper<>(Employee.class));
    }

    public int save(Employee employee) {
        String sql = "INSERT INTO employees (id, name, dob, address, department) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, employee.getId(), employee.getName(), employee.getDob(), employee.getAddress(), employee.getDepartment());
    }

    public int update(Employee employee) {
        String sql = "UPDATE employees SET name = ?, dob = ?, address = ?, department = ? WHERE id = ?";
        return jdbcTemplate.update(sql, employee.getName(), employee.getDob(), employee.getAddress(), employee.getDepartment(), employee.getId());
    }

    public int deleteById(String id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public int saveAll(List<Employee> employees) {
        String sql = "INSERT INTO employees (id, name, dob, address, department) VALUES (?, ?, ?, ?, ?)";
        int[] updateCounts = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Employee employee = employees.get(i);
                ps.setString(1, employee.getId());
                ps.setString(2, employee.getName());
                ps.setDate(3, java.sql.Date.valueOf(employee.getDob()));
                ps.setString(4, employee.getAddress());
                ps.setString(5, employee.getDepartment());
            }

            @Override
            public int getBatchSize() {
                return employees.size();
            }
        });
        return updateCounts.length;
    }
}
