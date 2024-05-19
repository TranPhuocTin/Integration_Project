package com.example.multidatasource.service.imp;

import com.example.multidatasource.entity.mysql.EmployeeEntity;
import com.example.multidatasource.entity.mysql.PayRateEntity;
import com.example.multidatasource.repository.pr_repo.EmployeeRepository;
import com.example.multidatasource.repository.pr_repo.PayRateRepository;
import com.example.multidatasource.service.PayrollService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayrollServiceImp implements PayrollService {
    private final EmployeeRepository employeeRepository;
    private final PayRateRepository payRateRepository;
    @Autowired
    public PayrollServiceImp(EmployeeRepository employeeRepository, PayRateRepository payRateRepository) {
        this.employeeRepository = employeeRepository;
        this.payRateRepository = payRateRepository;
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
    public PayRateEntity findByIdPayRates(int id) {
        return payRateRepository.findByIdPayRates(id);
    }

    @Override
    @Transactional(transactionManager="mysqlTransactionManager", rollbackFor = Exception.class)
    public EmployeeEntity updaEmployeeEntity(EmployeeEntity employeeEntity) {
        return employeeRepository.save(employeeEntity);
    }

    @Override
    public List<PayRateEntity> getAllPayRates() {
        return payRateRepository.findAll();
    }


}
