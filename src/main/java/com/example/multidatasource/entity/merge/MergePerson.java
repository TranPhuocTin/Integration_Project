package com.example.multidatasource.entity.merge;

import com.example.multidatasource.entity.mysql.PayRateEntity;
//import com.example.multidatasource.entity.sqlsever.BenefitPlanEntity;
import com.example.multidatasource.entity.sqlsever.BenefitPlanEntity;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MergePerson {
    private int personalId;
    private String currentFirstName;
    private String currentLastName;
    private String currentMiddleName;
    private Date birthDate;
    private String socialSecurityNumber;
    private double ssn;
    private String driversLicense;
    private String currentAddress1;
    private String currentAddress2;
    private String currentCity;
    private String currentCountry;
    private Long currentZip;
    private String currentGender;
    private String currentPhoneNumber;
    private String currentPersonalEmail;
    private String currentMaritalStatus;
    private String ethnicity;
    private Short shareholderStatus;
    private BenefitPlanEntity benefitPlan;
    private int employeeNumber;
    private String payRate;
    private Integer vacationDays;
    private Double paidToDate;
    private Double paidLastYear;
    private PayRateEntity payRates;
}
