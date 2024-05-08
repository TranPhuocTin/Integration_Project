package com.example.multidatasource.entity.mysql;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "`pay rates`")
public class PayRateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`idPay Rates`")
    private int idPayRates;

    @Column(name = "`Pay Rate Name`", nullable = false)
    private String payRateName;

    @Column(name = "`Value`", nullable = false)
    private long value;

    @Column(name = "`Tax Percentage`", nullable = false)
    private long taxPercentage;

    @Column(name = "`Pay Type`", nullable = false)
    private int payType;

    @Column(name = "`Pay Amount`", nullable = false)
    private long payAmount;

    @Column(name = "`PT - Level C`", nullable = false)
    private long ptLevelC;
}
