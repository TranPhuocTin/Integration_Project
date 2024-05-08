package com.example.multidatasource.service.imp;

import com.example.multidatasource.dto.MergeDTO;
import com.example.multidatasource.entity.merge.MergePerson;
import com.example.multidatasource.entity.mysql.EmployeeEntity;
import com.example.multidatasource.entity.sqlsever.PersonalEntity;
import com.example.multidatasource.repository.hrm_repo.PersonalRepository;
import com.example.multidatasource.repository.pr_repo.EmployeeRepository;
import com.example.multidatasource.service.PayrollService;
import com.example.multidatasource.service.MergeService;
import com.example.multidatasource.service.HumanResourceService;
//import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class MergeServiceImp implements MergeService {

    @Autowired
    PayrollService payrollService;

    @Autowired
    HumanResourceService humanResourceService;

    @Autowired
    PersonalRepository personalRepository;

    @Autowired
    EmployeeRepository employeeRepository;

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

        employeeEntityBuilder(mergePerson);

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
            return "EmployeeEntity or PersonalEntity not found";
        }
        try {
            humanResourceService.deletePersonalById(id);
            payrollService.deleteEmployeeById(id);
            return "Delete Successfully";
        } catch (Exception e) {
            return "Error deleting EmployeeEntity and PersonalEntity";
        }
    }

    EmployeeEntity employeeEntityBuilder(MergePerson mergePerson){
        return EmployeeEntity.builder().idEmployee(mergePerson.getPersonalId())
                .firstName(mergePerson.getCurrentFirstName())
                .lastName(mergePerson.getCurrentLastName())
                .build();
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
                .ssn(employeeEntity.getSsn())
                .payRates(employeeEntity.getPayRates()).build();
    }
}
