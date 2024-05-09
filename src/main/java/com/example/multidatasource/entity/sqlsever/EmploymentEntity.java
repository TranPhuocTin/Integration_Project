package com.example.multidatasource.entity.sqlsever;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "EMPLOYMENT")
public class EmploymentEntity {

    @Id
    @Column(name = "EMPLOYMENT_ID", nullable = false)
    private int employmentId;

    @Column(name = "EMPLOYMENT_CODE", length = 50)
    private String employmentCode;

    @Column(name = "EMPLOYMENT_STATUS", length = 10)
    private String employmentStatus;

    @Column(name = "HIRE_DATE_FOR_WORKING")
    private Date hireDateForWorking;

    @Column(name = "WORKERS_COMP_CODE", length = 10)
    private String workersCompCode;

    @Column(name = "TERMINATION_DATE")
    private Date terminationDate;

    @Column(name = "REHIRE_DATE_FOR_WORKING")
    private Date rehireDateForWorking;

    @Column(name = "LAST_REVIEW_DATE")
    private Date lastReviewDate;

    @Column(name = "NUMBER_DAYS_REQUIREMENT_OF_WORKING_PER_MONTH")
    private Long numberDaysRequirementOfWorkingPerMonth;

    @Column(name = "PERSONAL_ID")
    private int personal;
}
