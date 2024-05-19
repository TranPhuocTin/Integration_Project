package com.example.multidatasource.payload;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateEmploymentDetailsDTO {
    //Id
    private int employmentId;
    private Long jobHistoryId;
    private Long employmentWorkingTimeId;
    //Employment

    private String employmentCode;
    private String employmentStatus;
    private Date hireDateForWorking;
    private Date rehireDateForWorking;
    private Date lastReviewDate;
    private String workersCompCode;
    private Date terminationDate;
    //Job history
    private String department;
    private String division;
    private String jobTitle;
    private String supervisor;
    private String location;
    private Short typeOfWork;
    //Employment working time
    private Integer numberDaysRequirementOfWorkingPerMonth;
    private Date yearWorking;
    private Integer monthWorking;
    private Integer numberDaysActualOfWorkingPerMonth;
    private int totalNumberVacationWorkingDaysPerMonth;
    private Date fromDate;
    private Date thruDate;
}
