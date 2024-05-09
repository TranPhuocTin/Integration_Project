package com.example.multidatasource.controller;

import com.example.multidatasource.entity.sqlsever.EmploymentEntity;
import com.example.multidatasource.entity.sqlsever.EmploymentWorkingTimeEntity;
import com.example.multidatasource.entity.sqlsever.JobHistoryEntity;
import com.example.multidatasource.entity.sqlsever.PersonalEntity;
import com.example.multidatasource.service.HumanResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/human-resource")
public class HumanResourceController {
    private final HumanResourceService personalService;
    @Autowired
    public HumanResourceController(HumanResourceService personalService) {
        this.personalService = personalService;
    }

    //Get all personals in human resource
    @GetMapping("/get-all-personals")
    public List<PersonalEntity> getPersonal(){
        return personalService.getAllPersonals();
    }


    //Get job history by personalid
    @GetMapping("/get-job-history/{id}")
    public List<JobHistoryEntity> getJobHistoryByPersonalId(@PathVariable int id){
        return personalService.findJobHistoryByPersonalId(id);
    }

    //Get employment working time by personalid
    @GetMapping("/get-employment-working-time/{id}")
    public List<EmploymentWorkingTimeEntity> getEmploymentWorkingTimeByPersonalId(@PathVariable int id){
        return personalService.findEmploymentWorkingTimeByPersonalId(id);
    }

}
