package com.example.multidatasource.entity.sqlsever;

import com.example.multidatasource.entity.sqlsever.EmploymentEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "JOB_HISTORY")
public class JobHistoryEntity {

    @Id
    @Column(name = "JOB_HISTORY_ID", nullable = false)
    private Long jobHistoryId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMPLOYMENT_ID")
    private EmploymentEntity employment;

    @Column(name = "DEPARTMENT", length = 250)
    private String department;

    @Column(name = "DIVISION", length = 250)
    private String division;

    @Column(name = "FROM_DATE")
    private Date fromDate;

    @Column(name = "THRU_DATE")
    private Date thruDate;

    @Column(name = "JOB_TITLE", length = 250)
    private String jobTitle;

    @Column(name = "SUPERVISOR", length = 250)
    private String supervisor;

    @Column(name = "LOCATION", length = 250)
    private String location;

    @Column(name = "TYPE_OF_WORK")
    private Short typeOfWork;
}
