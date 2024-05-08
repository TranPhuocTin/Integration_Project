package com.example.multidatasource.service;

import com.example.multidatasource.entity.mysql.EmployeeEntity;

import java.util.List;

public interface PayrollService {
    List<EmployeeEntity> getAllEmployees();
    EmployeeEntity getEmployeeById(int id);

    String deleteEmployeeById(int id);

    void updateEmployee(EmployeeEntity employee);
}

