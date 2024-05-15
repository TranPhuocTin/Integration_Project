package com.example.multidatasource.service.imp;

import com.example.multidatasource.entity.mysql.EmployeeEntity;
import com.example.multidatasource.entity.mysql.PayRateEntity;
import com.example.multidatasource.entity.sqlsever.EmploymentWorkingTimeEntity;
import com.example.multidatasource.repository.hrm_repo.EmploymentWorkingTimeRepository;
import com.example.multidatasource.repository.pr_repo.EmployeeRepository;
import com.example.multidatasource.service.PayrollService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayrollServiceImp implements PayrollService {
    private final EmployeeRepository employeeRepository;
    @Autowired
    public PayrollServiceImp(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<EmployeeEntity> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public EmployeeEntity getEmployeeById(int id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(transactionManager="mysqlTransactionManager", rollbackFor = Exception.class)
    public String deleteEmployeeById(int id) {
        try {
            employeeRepository.deleteById(id);
            return "Deleted Successfully";
        } catch (Exception e) {
//            e.printStackTrace();
            return "Failed to delete";
        }
    }

    @Override
    @Transactional(transactionManager="mysqlTransactionManager", rollbackFor = Exception.class)
    public void updateEmployee(EmployeeEntity employee) {
        employeeRepository.save(employee);
    }

    @Override
    @Transactional(transactionManager="mysqlTransactionManager", rollbackFor = Exception.class)
    public boolean updatePayrateByEmployeeId(int employeeId, PayRateEntity payRate) {
        EmployeeEntity employee = employeeRepository.findByIdEmployee(employeeId);
        if(employee == null){
            return false;
        }
//        payRateRepository.save(payRate);
        employee.setPayRates(payRate);
        employeeRepository.save(employee);
        return true;
    }


}
