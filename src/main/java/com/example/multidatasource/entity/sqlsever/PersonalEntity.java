package com.example.multidatasource.entity.sqlsever;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "PERSONAL")
@Builder
public class PersonalEntity {

    @Id
    @Column(name = "PERSONAL_ID", nullable = false)
    private int personalId;

    @Column(name = "CURRENT_FIRST_NAME", length = 50)
    private String currentFirstName;

    @Column(name = "CURRENT_LAST_NAME")
    private String currentLastName;

    @Column(name = "CURRENT_MIDDLE_NAME", length = 50)
    private String currentMiddleName;

    @Column(name = "BIRTH_DATE")
    private Date birthDate;

    @Column(name = "SOCIAL_SECURITY_NUMBER", length = 20)
    private String socialSecurityNumber;

    @Column(name = "DRIVERS_LICENSE", length = 50)
    private String driversLicense;

    @Column(name = "CURRENT_ADDRESS_1", length = 255)
    private String currentAddress1;

    @Column(name = "CURRENT_ADDRESS_2", length = 255)
    private String currentAddress2;

    @Column(name = "CURRENT_CITY", length = 100)
    private String currentCity;

    @Column(name = "CURRENT_COUNTRY", length = 100)
    private String currentCountry;

    @Column(name = "CURRENT_ZIP")
    private Long currentZip;

    @Column(name = "CURRENT_GENDER", length = 20)
    private String currentGender;

    @Column(name = "CURRENT_PHONE_NUMBER", length = 15)
    private String currentPhoneNumber;

    @Column(name = "CURRENT_PERSONAL_EMAIL", length = 50)
    private String currentPersonalEmail;

    @Column(name = "CURRENT_MARITAL_STATUS", length = 50)
    private String currentMaritalStatus;

    @Column(name = "ETHNICITY", length = 255)
    private String ethnicity;

    @Column(name = "SHAREHOLDER_STATUS")
    private Short shareholderStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BENEFIT_PLAN_ID")
    private BenefitPlanEntity benefitPlan;

    @OneToMany(mappedBy = "personal", cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private List<EmploymentEntity> employmentEntityList;

    public String getEthnicity() {
        return ethnicity != null ? ethnicity.trim() : null;
    }
}
