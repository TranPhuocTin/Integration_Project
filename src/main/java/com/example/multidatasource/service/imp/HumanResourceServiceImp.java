package com.example.multidatasource.service.imp;

import com.example.multidatasource.entity.mysql.EmployeeEntity;
import com.example.multidatasource.entity.sqlsever.EmploymentEntity;
import com.example.multidatasource.entity.sqlsever.EmploymentWorkingTimeEntity;
import com.example.multidatasource.entity.sqlsever.JobHistoryEntity;
import com.example.multidatasource.entity.sqlsever.PersonalEntity;
import com.example.multidatasource.repository.hrm_repo.EmploymentRepository;
import com.example.multidatasource.repository.hrm_repo.EmploymentWorkingTimeRepository;
import com.example.multidatasource.repository.hrm_repo.JobHistoryRepository;
import com.example.multidatasource.repository.hrm_repo.PersonalRepository;
import com.example.multidatasource.repository.pr_repo.EmployeeRepository;
import com.example.multidatasource.service.HumanResourceService;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HumanResourceServiceImp implements HumanResourceService {

//    @Autowired
//    PersonalRepository personalRepo;

    @Autowired
    EmploymentWorkingTimeRepository employmentWorkingTimeRepo;

    @Autowired
    JobHistoryRepository jobHistoryRepo;

    @Autowired
    EmploymentRepository employmentRepo;

    @Autowired
    PersonalRepository personalRepo;

    @Override
    public List<PersonalEntity> getAllPersonals(){
        return personalRepo.findAll();
    }

    @Override
    public List<JobHistoryEntity> getAllJobHistories() {
        return jobHistoryRepo.findAll();
    }

    @Override
    public List<EmploymentEntity> getAllEmployments() {
        return employmentRepo.findAll();
    }

    @Override
    public List<EmploymentWorkingTimeEntity> getAllEmploymentWorkingTimes() {
        return employmentWorkingTimeRepo.findAll();
    }

    @Override
    public PersonalEntity getPersonalById(int id) {
        return personalRepo.findById(id).orElse(null);
    }

    @Override
    @Transactional(transactionManager = "sqlServerTransactionManager", rollbackFor = Exception.class)
    public String deletePersonalById(int id) {
        EmploymentEntity employmentEntity = employmentRepo.findById(id).orElse(null);
        if(employmentEntity == null){
            //If the personal don't have employment, just delete personal
            personalRepo.deleteByPersonalId(id);
            return "Delete Successfully";
        }
        else{
            //Delete personal in hrm databases
            jobHistoryRepo.deleteByEmployment(employmentEntity);
            employmentWorkingTimeRepo.deleteByEmployment(employmentEntity);
            personalRepo.deleteByPersonalId(id);

            return "Delete Successfully";

        }
    }

    @Override
    @Transactional(value = "sqlServerTransactionManager", rollbackFor = Exception.class)
    public void updatePersonal(PersonalEntity personal) {
        personalRepo.save(personal);
    }

}
