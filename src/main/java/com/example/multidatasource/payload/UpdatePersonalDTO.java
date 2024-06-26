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
public class UpdatePersonalDTO {
    private int personalId;
    private String currentFirstName;
    private String currentLastName;
    private String currentMiddleName;
    private Date birthDate;
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
