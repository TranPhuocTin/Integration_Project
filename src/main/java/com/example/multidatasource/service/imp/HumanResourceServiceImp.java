package com.example.multidatasource.service.imp;

import com.example.multidatasource.entity.mysql.EmployeeEntity;
import com.example.multidatasource.entity.sqlsever.*;
import com.example.multidatasource.repository.hrm_repo.*;
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
    @Transactional(value = "sqlServerTransactionManager", rollbackFor = Exception.class)
    public boolean updateBenefitPlanByPersonalId(int personalId, BenefitPlanEntity benefitPlan) {
        PersonalEntity personal = personalRepo.findByPersonalId(personalId);
        if(personal != null){
//            benefitPlanRepo.save(benefitPlan);
            personal.setBenefitPlan(benefitPlan);
            personalRepo.save(personal);
            return true;
        }
        return false;
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

    @Override
    public boolean updateEmployment(EmploymentEntity employment) {
        employmentRepo.save(employment);
        return true;
    }

    @Override
    public EmploymentEntity findByEmploymentId(int id) {
        return employmentRepo.findByEmploymentId(id);
    }

    @Override
    public boolean updateJobHistory(JobHistoryEntity jobHistory) {
        jobHistoryRepo.save(jobHistory);
        return true;
    }

    @Override
    public boolean updateEmploymentWorkingTime(EmploymentWorkingTimeEntity employmentWorkingTime) {
        employmentWorkingTimeRepo.save(employmentWorkingTime);
        return true;
    }

    @Override
    public JobHistoryEntity findByJobHistoryId(Long id) {
        return jobHistoryRepo.findByJobHistoryId(id);
    }

    @Override
    public EmploymentWorkingTimeEntity findByEmploymentWorkingTimeId(Long id) {
        return employmentWorkingTimeRepo.findByEmploymentWorkingTimeId(id);
    }

    @Override
    public EmploymentWorkingTimeEntity findEmploymentWorkingTimeByEmployment(EmploymentEntity employment) {
        return employmentWorkingTimeRepo.findByEmployment(employment);
    }

    @Override
    public JobHistoryEntity findJobHistoryByEmployment(EmploymentEntity employment) {
        return jobHistoryRepo.findByEmployment(employment);
    }


}
