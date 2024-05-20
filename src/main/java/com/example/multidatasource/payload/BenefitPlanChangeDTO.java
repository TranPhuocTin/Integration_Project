package com.example.multidatasource.payload;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BenefitPlanChangeDTO {
    private String oldBenefitPlan;
    private String newBenefitPlan;
    private String changeDate;
}