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
    private int benefitPlansId;
    private int idPayRates;
}
