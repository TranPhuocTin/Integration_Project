package com.example.multidatasource.entity.sqlsever;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "EMPLOYMENT_WORKING_TIME")
public class EmploymentWorkingTimeEntity {

    @Id
    @Column(name = "EMPLOYMENT_WORKING_TIME_ID", nullable = false)
    private Long employmentWorkingTimeId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "EMPLOYMENT_ID")
    private EmploymentEntity employment;

    @Column(name = "YEAR_WORKING")
    private Date yearWorking;

    @Column(name = "MONTH_WORKING")
    private Integer monthWorking;

    @Column(name = "NUMBER_DAYS_ACTUAL_OF_WORKING_PER_MONTH")
    private Integer numberDaysActualOfWorkingPerMonth;

    @Column(name = "TOTAL_NUMBER_VACATION_WORKING_DAYS_PER_MONTH")
    private Integer totalNumberVacationWorkingDaysPerMonth;
}
