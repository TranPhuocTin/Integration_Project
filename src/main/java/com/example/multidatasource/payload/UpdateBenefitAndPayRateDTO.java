package com.example.multidatasource.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateBenefitAndPayRateDTO {
    //Benefit Plan
    private int benefitPlansId;

    private String planName;

    private Double deductible;

    private Long percentageCopay;
    //Pay Rate
    private int idPayRates;

    private String payRateName;

    private long value;

    private long taxPercentage;

    private int payType;

    private long payAmount;

    private long ptLevelC;
    //Employee
    private double paidToDate;

    private double paidLastYear;
}
