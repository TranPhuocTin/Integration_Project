package com.example.multidatasource.controller;

import com.example.multidatasource.entity.sqlsever.EmploymentWorkingTimeEntity;
import com.example.multidatasource.entity.sqlsever.JobHistoryEntity;
import com.example.multidatasource.entity.sqlsever.PersonalEntity;
import com.example.multidatasource.repository.hrm_repo.JobHistoryRepository;
import com.example.multidatasource.service.HumanResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/human-resource")
public class HumanResourceController {
    private final HumanResourceService humanresourceService;
    @Autowired
    public HumanResourceController(HumanResourceService humanresourceService) {
        this.humanresourceService = humanresourceService;
    }

    @Autowired
    JobHistoryRepository jobHistoryRepository;

    //Get all personals in human resource
    @GetMapping("/get-all-personals")
    public ResponseEntity<List<PersonalEntity>> getPersonal(){
        return ResponseEntity.ok(humanresourceService.getAllPersonals());
    }


    //Get job history by personalid
    @GetMapping("/get-job-history/{id}")
    public ResponseEntity<List<JobHistoryEntity>> getJobHistoryByPersonalId(@PathVariable int id){
        return ResponseEntity.ok(humanresourceService.findJobHistoryByPersonalId(id));
    }

    //Get employment working time by personalid
    @GetMapping("/get-employment-working-time/{id}")
    public ResponseEntity<List<EmploymentWorkingTimeEntity>> getEmploymentWorkingTimeByPersonalId(@PathVariable int id){
        return ResponseEntity.ok(humanresourceService.findEmploymentWorkingTimeByPersonalId(id));
    }

    @GetMapping("/get-all-job-histories")
    public ResponseEntity<List<JobHistoryEntity>> getAllJobHistories(){
        return ResponseEntity.ok(humanresourceService.getAllJobHistories());
    }

    @GetMapping("/get-all-employment-working-time")
    public ResponseEntity<List<EmploymentWorkingTimeEntity>> getAllEmploymentWorkingTime(){
        return ResponseEntity.ok(humanresourceService.getAllEmploymentWorkingTime());
    }
}
