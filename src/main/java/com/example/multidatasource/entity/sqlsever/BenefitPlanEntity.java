package com.example.multidatasource.entity.sqlsever;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "BENEFIT_PLANS")
public class BenefitPlanEntity {

    @Id
    @Column(name = "BENEFIT_PLANS_ID", nullable = false)
    private int benefitPlansId;

    @Column(name = "PLAN_NAME")
    private String planName;

    @Column(name = "DEDUCTABLE")
    private Double deductible;

    @Column(name = "PERCENTAGE_COPAY")
    private Long percentageCopay;
}
