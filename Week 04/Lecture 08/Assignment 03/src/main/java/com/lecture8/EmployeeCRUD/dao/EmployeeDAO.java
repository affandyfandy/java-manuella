package com.lecture8.EmployeeCRUD.dao;

import com.lecture8.EmployeeCRUD.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        String sql = "INSERT INTO employees (id, name, dob, address, department) VALUES (?, ?, ?, ?, ?)";
        int[] updateCounts = jdbcTemplate1.batchUpdate(sql, new BatchPreparedStatementSetter() {
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


    @Transactional(transactionManager = "chainedTransactionManager", rollbackFor = Exception.class)
    public void transferEmployeesToNewDatabase() {
        try {
            String sqlSelect = "SELECT * FROM employees WHERE department = ?";
            List<Employee> engineeringEmployees = jdbcTemplate1.query(sqlSelect, new Object[]{"Engineering"},
                    new BeanPropertyRowMapper<>(Employee.class));

            String sqlInsert = "INSERT INTO employees (id, name, dob, address, department) VALUES (?, ?, ?, ?, ?)";
            for (Employee employee : engineeringEmployees) {
                jdbcTemplate2.update(sqlInsert, employee.getId(), employee.getName(), employee.getDob(),
                        employee.getAddress(), employee.getDepartment());
            }

            String sqlDelete = "DELETE FROM employees WHERE id = ?";
            for (Employee employee : engineeringEmployees) {
                jdbcTemplate1.update(sqlDelete, employee.getId());
            }

        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to transfer employees: " + e.getMessage(), e);
        }
    }


    @Transactional(transactionManager = "chainedTransactionManager", rollbackFor = Exception.class)
    public void transferEmployeesToNewDatabaseFail() {
        try {
            String sqlSelect = "SELECT * FROM employees WHERE department = ?";
            List<Employee> engineeringEmployees = jdbcTemplate1.query(sqlSelect, new Object[]{"Engineering"},
                    new BeanPropertyRowMapper<>(Employee.class));

            String sqlInsert = "INSERT INTO employees (id, name, dob, address, department) VALUES (?, ?, ?, ?, ?)";
            for (Employee employee : engineeringEmployees) {
                jdbcTemplate2.update(sqlInsert, employee.getId(), employee.getName(), employee.getDob(),
                        employee.getAddress(), employee.getDepartment());
            }

            String sqlDelete = "DELETE FROM wrongtable WHERE id = ?";
            for (Employee employee : engineeringEmployees) {
                jdbcTemplate1.update(sqlDelete, employee.getId());
            }

        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to transfer employees: " + e.getMessage(), e);
        }
    }
}