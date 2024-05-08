package com.example.multidatasource.controller;

//import com.example.multidatasource.entity.merge.EmployeePersonalDTO;
import com.example.multidatasource.entity.mysql.EmployeeEntity;
import com.example.multidatasource.service.PayrollService;
//import com.example.multidatasource.service.MergeEmployeePersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payroll")
public class PayrollController {
    @Autowired
    PayrollService employeeService;

    @GetMapping("/get-all-employees")
    public List<EmployeeEntity> getAllEmployees(){
        return employeeService.getAllEmployees();
    }
}
