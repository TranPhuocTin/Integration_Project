package com.example.multidatasource.service;

import com.example.multidatasource.entity.sqlsever.*;

import java.util.List;

public interface HumanResourceService {
    List<PersonalEntity> getAllPersonals();

    PersonalEntity getPersonalById(int id);

    String deletePersonalById(int id);

    void updatePersonal(PersonalEntity personal);

    boolean updateBenefitPlanByPersonalId(int personalId, BenefitPlanEntity benefitPlan);

    List<JobHistoryEntity> findJobHistoryByPersonalId(int id);

    List<EmploymentWorkingTimeEntity> findEmploymentWorkingTimeByPersonalId(int id);

    List<JobHistoryEntity> getAllJobHistories();

    List<EmploymentWorkingTimeEntity> getAllEmploymentWorkingTime();

    boolean updateEmployment(EmploymentEntity employment);

    EmploymentEntity findByEmploymentId(int id);

    boolean updateJobHistory(JobHistoryEntity jobHistory);

    boolean updateEmploymentWorkingTime(EmploymentWorkingTimeEntity employmentWorkingTime);

    JobHistoryEntity findByJobHistoryId(Long id);

    EmploymentWorkingTimeEntity findByEmploymentWorkingTimeId(Long id);

    EmploymentWorkingTimeEntity findEmploymentWorkingTimeByEmployment(EmploymentEntity employment);
    JobHistoryEntity findJobHistoryByEmployment(EmploymentEntity employment);
}
