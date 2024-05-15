package com.example.multidatasource.payload;

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
    private String jobTitle;
    private String supervisor;
    private Short typeOfWork;
    private String location;
    //Employment working time
    private Date yearWorking;
    private Integer monthWorking;
    private Integer numberDaysActualOfWorkingPerMonth;
    private int totalNumberVacationWorkingDaysPerMonth;
}
