package com.example.multidatasource.dto;

import com.example.multidatasource.entity.mysql.PayRateEntity;
import com.example.multidatasource.entity.sqlsever.BenefitPlanEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MergeDTO {
    private int personalId;
    private String currentFirstName;
    private String currentLastName;
    private String currentMiddleName;
    private Date birthDate;
    private String socialSecurityNumber;
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
}
