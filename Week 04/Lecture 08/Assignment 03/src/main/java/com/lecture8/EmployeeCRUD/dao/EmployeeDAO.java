package com.lecture8.EmployeeCRUD.dao;

import com.lecture8.EmployeeCRUD.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class EmployeeDAO {

    @Autowired
    @Qualifier("jdbcTemplate1")
    private JdbcTemplate jdbcTemplate1;

    @Autowired
    @Qualifier("jdbcTemplate2")
    private JdbcTemplate jdbcTemplate2;

    public List<Employee> findAll() {
        String sql = "SELECT * FROM employees";
        return jdbcTemplate1.query(sql, new BeanPropertyRowMapper<>(Employee.class));
    }

    public Employee findById(String id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        return jdbcTemplate1.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Employee.class));
    }

    public List<Employee> findByDepartment(String department) {
        String sql = "SELECT * FROM employees WHERE department = ?";
        return jdbcTemplate1.query(sql, new Object[]{department}, new BeanPropertyRowMapper<>(Employee.class));
    }

    @Transactional(transactionManager = "transactionManager1")
    public int save(Employee employee) {
        String sql = "INSERT INTO employees (id, name, dob, address, department) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate1.update(sql, employee.getId(), employee.getName(), employee.getDob(), employee.getAddress(), employee.getDepartment());
    }

    @Transactional(transactionManager = "transactionManager1")
    public int update(Employee employee) {
        String sql = "UPDATE employees SET name = ?, dob = ?, address = ?, department = ? WHERE id = ?";
        return jdbcTemplate1.update(sql, employee.getName(), employee.getDob(), employee.getAddress(), employee.getDepartment(), employee.getId());
    }

    @Transactional(transactionManager = "transactionManager1")
    public int deleteById(String id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        return jdbcTemplate1.update(sql, id);
    }

    @Transactional(transactionManager = "transactionManager1")
    public int saveAll(List<Employee> employees) {
        for (Employee employee : employees) {
            save(employee);
        }
        return employees.size();
    }

    @Transactional(transactionManager = "transactionManager1")
    public void transferEmployeesToNewDatabase() {
        // Fetch employees with department = 'engineering' from jdbcTemplate1
        String sqlSelect = "SELECT * FROM employees WHERE department = ?";
        List<Employee> engineeringEmployees = jdbcTemplate1.query(sqlSelect, new Object[]{"Engineering"},
                new BeanPropertyRowMapper<>(Employee.class));

        // Insert employees into jdbcTemplate2
        String sqlInsert = "INSERT INTO employees (id, name, dob, address, department) VALUES (?, ?, ?, ?, ?)";
        for (Employee employee : engineeringEmployees) {
            jdbcTemplate2.update(sqlInsert, employee.getId(), employee.getName(), employee.getDob(),
                    employee.getAddress(), employee.getDepartment());
        }

        // Delete transferred employees from jdbcTemplate1
        String sqlDelete = "DELETE FROM employees WHERE id = ?";
        for (Employee employee : engineeringEmployees) {
            jdbcTemplate1.update(sqlDelete, employee.getId());
        }
    }
}