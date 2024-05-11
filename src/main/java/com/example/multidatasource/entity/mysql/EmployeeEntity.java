package com.example.multidatasource.entity.mysql;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "employee")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmployee")
    private int idEmployee;

    @Column(name = "`Employee Number`", nullable = false, unique = true)
    private int employeeNumber;

    @Column(name = "`Last Name`", nullable = false)
    private String lastName;

    @Column(name = "`First Name`", nullable = false)
    private String firstName;

    @Column(name = "SSN", nullable = false)
    private double ssn;

    @Column(name = "Pay Rate")
    private String payRate;

    @Column(name = "`Vacation Days`")
    private Integer vacationDays;

    @Column(name = "`Paid To Date`")
    private Double paidToDate;

    @Column(name = "`Paid Last Year`")
    private Double paidLastYear;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "`Pay Rates_idPay Rates`")
    private PayRateEntity payRates;
}
