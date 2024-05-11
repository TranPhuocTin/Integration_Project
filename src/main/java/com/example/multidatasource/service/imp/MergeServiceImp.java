package com.example.multidatasource.service.imp;

import com.example.multidatasource.entity.merge.MergePerson;
import com.example.multidatasource.entity.mysql.EmployeeEntity;
import com.example.multidatasource.entity.mysql.PayRateEntity;
import com.example.multidatasource.entity.sqlsever.BenefitPlanEntity;
import com.example.multidatasource.entity.sqlsever.PersonalEntity;
import com.example.multidatasource.repository.hrm_repo.PersonalRepository;
import com.example.multidatasource.repository.pr_repo.EmployeeRepository;
import com.example.multidatasource.service.PayrollService;
import com.example.multidatasource.service.MergeService;
import com.example.multidatasource.service.HumanResourceService;
//import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class MergeServiceImp implements MergeService {

    private final PayrollService payrollService;
    private final HumanResourceService humanResourceService;
    private final PersonalRepository personalRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public MergeServiceImp(PayrollService payrollService, HumanResourceService humanResourceService, PersonalRepository personalRepository, EmployeeRepository employeeRepository) {
        this.payrollService = payrollService;
        this.humanResourceService = humanResourceService;
        this.personalRepository = personalRepository;
        this.employeeRepository = employeeRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(MergeServiceImp.class);


    @Override
    public List<MergePerson> mergeEmployeePersonal() {
        List<EmployeeEntity> employeeEntities = payrollService.getAllEmployees();
        List<PersonalEntity> personalEntities = humanResourceService.getAllPersonals();
        List<MergePerson> returnValue = new ArrayList<>();
        

        for (PersonalEntity personalEntity : personalEntities) {
            MergePerson mergePerson = new MergePerson();
            // Find the corresponding EmployeeEntity
            EmployeeEntity matchingEmployeeEntity = employeeEntities.stream()
                    .filter(employeeEntity -> employeeEntity.getIdEmployee() == personalEntity.getPersonalId())
                    .findFirst()
                    .orElse(null);

            if (matchingEmployeeEntity != null && matchingEmployeeEntity.getIdEmployee() == personalEntity.getPersonalId()) {
                mergePerson = mergePersonBuilder(matchingEmployeeEntity, personalEntity);
                returnValue.add(mergePerson);
            }
        }

        return returnValue;
    }

    @Override
    //Because the mothod contain 2 transactional, we need to specify the transactionManager
    public boolean updateEmployeePersonal(MergePerson mergePerson, int id) {
        EmployeeEntity employeeEntity = payrollService.getEmployeeById(id);
        PersonalEntity personalEntity = humanResourceService.getPersonalById(id);

        BeanUtils.copyProperties(mergePerson, personalEntity);

        personalEntity.setPersonalId(id);

        employeeEntity.setFirstName(mergePerson.getCurrentFirstName());
        employeeEntity.setLastName(mergePerson.getCurrentLastName());

        try {
            humanResourceService.updatePersonal(personalEntity);
            payrollService.updateEmployee(employeeEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }



    @Override
    public String deleteEmployeePersonal(int id) {
        if(personalRepository.findById(id).isEmpty() || employeeRepository.findById(id).isEmpty()){
            return "Cannot find EmployeeEntity or PersonalEntity with id: " + id + " to delete";
        }
        try {
            humanResourceService.deletePersonalById(id);
            payrollService.deleteEmployeeById(id);
            return "Delete Successfully";
        } catch (Exception e) {
            return "Error deleting EmployeeEntity and PersonalEntity";
        }
    }

    @Override
    public MergePerson getMergePersonById(int id) {
        EmployeeEntity employeeEntity = payrollService.getEmployeeById(id);
        PersonalEntity personalEntity = humanResourceService.getPersonalById(id);

        if (employeeEntity == null || personalEntity == null) {
            return null;
        }

        return mergePersonBuilder(employeeEntity, personalEntity);
    }

    @Override
    public boolean updateBenefitPlanPayrate(int id, BenefitPlanEntity benefitPlan, PayRateEntity payRate, double paidToDate, double paidLastYear) {
        EmployeeEntity employeeEntity = payrollService.getEmployeeById(id);

        employeeEntity.setPaidToDate(paidToDate);
        employeeEntity.setPaidLastYear(paidLastYear);

        try {
            humanResourceService.updateBenefitPlanByPersonalId(id, benefitPlan);
            payrollService.updateEmployee(employeeEntity);
            payrollService.updatePayrateByEmployeeId(id, payRate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public MergePerson mergePersonBuilder(EmployeeEntity employeeEntity, PersonalEntity personalEntity){
        return MergePerson.builder().personalId(personalEntity.getPersonalId())
                .currentFirstName(personalEntity.getCurrentFirstName())
                .currentMiddleName(personalEntity.getCurrentMiddleName())
                .currentLastName(personalEntity.getCurrentLastName())
                .birthDate(personalEntity.getBirthDate())
                .socialSecurityNumber(personalEntity.getSocialSecurityNumber())
                .driversLicense(personalEntity.getDriversLicense())
                .currentAddress1(personalEntity.getCurrentAddress1())
                .currentAddress2(personalEntity.getCurrentAddress2())
                .currentCity(personalEntity.getCurrentCity())
                .currentCountry(personalEntity.getCurrentCountry())
                .currentZip(personalEntity.getCurrentZip())
                .currentGender(personalEntity.getCurrentGender())
                .currentPhoneNumber(personalEntity.getCurrentPhoneNumber())
                .currentPersonalEmail(personalEntity.getCurrentPersonalEmail())
                .currentMaritalStatus(personalEntity.getCurrentMaritalStatus())
                .ethnicity(personalEntity.getEthnicity())
                .shareholderStatus(personalEntity.getShareholderStatus())
                .benefitPlan(personalEntity.getBenefitPlan())
                .employeeNumber(employeeEntity.getEmployeeNumber())
                .payRate(employeeEntity.getPayRate())
                .vacationDays(employeeEntity.getVacationDays())
                .paidToDate(employeeEntity.getPaidToDate())
                .paidLastYear(employeeEntity.getPaidLastYear())
                .payRates(employeeEntity.getPayRates())
                .employmentEntityList(personalEntity.getEmploymentEntityList()).
                build();
    }
}
