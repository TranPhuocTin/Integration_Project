package com.example.multidatasource.service;

import com.example.multidatasource.entity.mysql.EmployeeEntity;
import com.example.multidatasource.entity.mysql.PayRateEntity;
import com.example.multidatasource.entity.sqlsever.EmploymentWorkingTimeEntity;

import java.util.List;

public interface PayrollService {
    List<EmployeeEntity> getAllEmployees();
    EmployeeEntity getEmployeeById(int id);

    String deleteEmployeeById(int id);

    void updateEmployee(EmployeeEntity employee);

    PayRateEntity findByIdPayRates(int id);

    EmployeeEntity updaEmployeeEntity (EmployeeEntity employeeEntity);

    List<PayRateEntity> getAllPayRates();
}

