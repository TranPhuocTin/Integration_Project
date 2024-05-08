package com.example.multidatasource.controller;

import com.example.multidatasource.entity.sqlsever.EmploymentEntity;
import com.example.multidatasource.entity.sqlsever.EmploymentWorkingTimeEntity;
import com.example.multidatasource.entity.sqlsever.JobHistoryEntity;
import com.example.multidatasource.entity.sqlsever.PersonalEntity;
import com.example.multidatasource.service.HumanResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/human-resource")
public class HumanResourceController {
    @Autowired
    HumanResourceService personalService;


    @GetMapping("/get-all-personals")
    public List<PersonalEntity> getPersonal(){
        return personalService.getAllPersonals();
    }

    //This method is used to get data from 2 tables in sqlsever database: job-history, employment
    @GetMapping("/get-job-histories")
    public List<JobHistoryEntity> getJobHistories(){
        return personalService.getAllJobHistories();
    }

    // This method is used to get data from 3 tables in sqlsever database: employment, personal, benefits-plan
    @GetMapping("/get-employments")
    public List<EmploymentEntity> getEmployments(){
        return personalService.getAllEmployments();
    }

    //This method is used to get data from 3 tables in sqlsever database: employment-working-time, employment, personal
    @GetMapping("/get-all-employment-working-time")
    public List<EmploymentWorkingTimeEntity> getEmploymentWorkingTime(){
        return personalService.getAllEmploymentWorkingTimes();
    }
}
