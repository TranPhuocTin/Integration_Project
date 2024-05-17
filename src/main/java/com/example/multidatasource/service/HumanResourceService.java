package com.example.multidatasource.service;

import com.example.multidatasource.entity.mysql.PayRateEntity;
import com.example.multidatasource.entity.sqlsever.*;

import java.util.List;

public interface HumanResourceService {
    List<PersonalEntity> getAllPersonals();

    PersonalEntity getPersonalById(int id);

    String deletePersonalById(int id);

    void updatePersonal(PersonalEntity personal);

    List<JobHistoryEntity> findJobHistoryByPersonalId(int id);

    List<EmploymentWorkingTimeEntity> findEmploymentWorkingTimeByPersonalId(int id);

    List<JobHistoryEntity> getAllJobHistories();

    List<EmploymentWorkingTimeEntity> getAllEmploymentWorkingTime();

    EmploymentEntity findByEmploymentId(int id);

    boolean updateJobHistory(JobHistoryEntity jobHistory);

    boolean updateEmploymentWorkingTime(EmploymentWorkingTimeEntity employmentWorkingTime);

    JobHistoryEntity findByJobHistoryId(Long id);

    EmploymentWorkingTimeEntity findByEmploymentWorkingTimeId(Long id);

    BenefitPlanEntity findByBenefitPlansId(int id);
}
