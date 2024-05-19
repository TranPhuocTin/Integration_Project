package com.example.multidatasource.controller;

import com.example.multidatasource.entity.mysql.EmployeeEntity;
import com.example.multidatasource.entity.mysql.PayRateEntity;
import com.example.multidatasource.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payroll")
public class PayrollController {
    private final PayrollService employeeService;
    @Autowired
    public PayrollController(PayrollService employeeService) {
        this.employeeService = employeeService;
    }

    //Get all employees
    @GetMapping("/get-all-employees")
    public ResponseEntity<List<EmployeeEntity>> getAllEmployees(){
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/get-all-payrates")
    public ResponseEntity<List<PayRateEntity>> getAllPayRates(){
        return ResponseEntity.ok(employeeService.getAllPayRates());
    }
}
