package com.example.multidatasource.service;

import com.example.multidatasource.entity.sqlsever.*;

import java.util.List;

public interface HumanResourceService {
    List<PersonalEntity> getAllPersonals();
    List<JobHistoryEntity> getAllJobHistories();
    List<EmploymentEntity> getAllEmployments();
    List<EmploymentWorkingTimeEntity> getAllEmploymentWorkingTimes();

    PersonalEntity getPersonalById(int id);

    String deletePersonalById(int id);

    void updatePersonal(PersonalEntity personal);
}
