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

    private final EmploymentWorkingTimeRepository employmentWorkingTimeRepo;
    private final JobHistoryRepository jobHistoryRepo;
    private final EmploymentRepository employmentRepo;
    private final PersonalRepository personalRepo;

    @Autowired
    public HumanResourceServiceImp(EmploymentWorkingTimeRepository employmentWorkingTimeRepo, JobHistoryRepository jobHistoryRepo, EmploymentRepository employmentRepo, PersonalRepository personalRepo) {
        this.employmentWorkingTimeRepo = employmentWorkingTimeRepo;
        this.jobHistoryRepo = jobHistoryRepo;
        this.employmentRepo = employmentRepo;
        this.personalRepo = personalRepo;
    }

    @Override
    public List<PersonalEntity> getAllPersonals(){
        return personalRepo.findAll();
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

    @Override
    public List<JobHistoryEntity> findJobHistoryByPersonalId(int id) {
        return jobHistoryRepo.findByEmployment_Personal(id);
    }

    @Override
    public List<EmploymentWorkingTimeEntity> findEmploymentWorkingTimeByPersonalId(int id) {
        return employmentWorkingTimeRepo.findByEmployment_Personal(id);
    }

    @Override
    public List<JobHistoryEntity> getAllJobHistories() {
        return jobHistoryRepo.findAllBy();
    }

    @Override
    public List<EmploymentWorkingTimeEntity> getAllEmploymentWorkingTime() {
        return employmentWorkingTimeRepo.findAllBy();
    }

}
