package com.example.multidatasource.service;

import com.example.multidatasource.entity.sqlsever.*;

import java.util.List;

public interface HumanResourceService {
    List<PersonalEntity> getAllPersonals();

    PersonalEntity getPersonalById(int id);

    String deletePersonalById(int id);

    void updatePersonal(PersonalEntity personal);

    List<JobHistoryEntity> findJobHistoryByPersonalId(int id);

    List<EmploymentWorkingTimeEntity> findEmploymentWorkingTimeByPersonalId(int id);
}