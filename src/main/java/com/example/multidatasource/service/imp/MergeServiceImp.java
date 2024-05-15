package com.example.multidatasource.service.imp;

import com.example.multidatasource.entity.sqlsever.*;
import com.example.multidatasource.payload.MergePersonDTO;
import com.example.multidatasource.entity.mysql.EmployeeEntity;
import com.example.multidatasource.entity.mysql.PayRateEntity;
import com.example.multidatasource.payload.UpdateBenefitAndPayRateDTO;
import com.example.multidatasource.payload.UpdateEmploymentDetailsDTO;
import com.example.multidatasource.repository.hrm_repo.EmploymentRepository;
import com.example.multidatasource.repository.hrm_repo.EmploymentWorkingTimeRepository;
import com.example.multidatasource.repository.hrm_repo.JobHistoryRepository;
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
    private static final Logger logger = LoggerFactory.getLogger(MergeServiceImp.class);
    private final PayrollService payrollService;
    private final HumanResourceService humanResourceService;
    private final PersonalRepository personalRepository;
    private final EmployeeRepository employeeRepository;
    private final EmploymentWorkingTimeRepository employmentWorkingTimeRepository;
    private final JobHistoryRepository jobHistoryRepository;
    private final EmploymentRepository employmentRepository;

    @Autowired
    public MergeServiceImp(PayrollService payrollService, HumanResourceService humanResourceService, PersonalRepository personalRepository, EmployeeRepository employeeRepository, EmploymentWorkingTimeRepository employmentWorkingTimeRepository, JobHistoryRepository jobHistoryRepository, EmploymentRepository employmentRepository) {
        this.payrollService = payrollService;
        this.humanResourceService = humanResourceService;
        this.personalRepository = personalRepository;
        this.employeeRepository = employeeRepository;
        this.employmentWorkingTimeRepository = employmentWorkingTimeRepository;
        this.jobHistoryRepository = jobHistoryRepository;
        this.employmentRepository = employmentRepository;
    }


    @Override
    public List<MergePersonDTO> mergeEmployeePersonal() {
        List<EmployeeEntity> employeeEntities = payrollService.getAllEmployees();
        List<PersonalEntity> personalEntities = humanResourceService.getAllPersonals();
        List<MergePersonDTO> returnValue = new ArrayList<>();
        

        for (PersonalEntity personalEntity : personalEntities) {
            MergePersonDTO mergePersonDTO = new MergePersonDTO();
            // Find the corresponding EmployeeEntity
            EmployeeEntity matchingEmployeeEntity = employeeEntities.stream()
                    .filter(employeeEntity -> employeeEntity.getIdEmployee() == personalEntity.getPersonalId())
                    .findFirst()
                    .orElse(null);

            if (matchingEmployeeEntity != null && matchingEmployeeEntity.getIdEmployee() == personalEntity.getPersonalId()) {
                mergePersonDTO = mergePersonBuilder(matchingEmployeeEntity, personalEntity);
                returnValue.add(mergePersonDTO);
            }
        }

        return returnValue;
    }

    @Override
    //Because the method contain 2 transactional, we need to specify the transactionManager
    public boolean updateEmployeePersonal(MergePersonDTO mergePersonDTO, int id) {
        EmployeeEntity employeeEntity = payrollService.getEmployeeById(id);
        PersonalEntity personalEntity = humanResourceService.getPersonalById(id);

        BeanUtils.copyProperties(mergePersonDTO, personalEntity);

        personalEntity.setPersonalId(id);
        employeeEntity.setFirstName(mergePersonDTO.getCurrentFirstName());
        employeeEntity.setLastName(mergePersonDTO.getCurrentLastName());

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
    public MergePersonDTO getMergePersonById(int id) {
        EmployeeEntity employeeEntity = payrollService.getEmployeeById(id);
        PersonalEntity personalEntity = humanResourceService.getPersonalById(id);

        if (employeeEntity == null || personalEntity == null) {
            return null;
        }

        return mergePersonBuilder(employeeEntity, personalEntity);
    }

    @Override
    public boolean updateBenefitPlanPayrate(int id, UpdateBenefitAndPayRateDTO updateBenefitAndPayrateDTO) {
        EmployeeEntity employeeEntity = payrollService.getEmployeeById(id);

        BenefitPlanEntity benefitPlanUpdate = new BenefitPlanEntity();
        PayRateEntity payRateEntityUpdate = new PayRateEntity();

        BeanUtils.copyProperties(updateBenefitAndPayrateDTO, benefitPlanUpdate);
        BeanUtils.copyProperties(updateBenefitAndPayrateDTO, payRateEntityUpdate);

        employeeEntity.setPaidToDate(updateBenefitAndPayrateDTO.getPaidToDate());
        employeeEntity.setPaidLastYear(updateBenefitAndPayrateDTO.getPaidLastYear());

        try {
            humanResourceService.updateBenefitPlanByPersonalId(id,benefitPlanUpdate);
            payrollService.updateEmployee(employeeEntity);
            payrollService.updatePayrateByEmployeeId(id,payRateEntityUpdate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public String updateEmploymentDetails(int id, UpdateEmploymentDetailsDTO updateEmploymentDetailsDTO) {
        PersonalEntity personalEntity = humanResourceService.getPersonalById(id);
        if (personalEntity == null) {
            return "Cannot find PersonalEntity with id: " + id;
        }

        List<EmploymentEntity> employmentEntityList = personalEntity.getEmploymentEntityList();
        if (employmentEntityList != null) {
            EmploymentEntity existingEmploymentEntity = humanResourceService.findByEmploymentId(updateEmploymentDetailsDTO.getEmploymentId());
            if (existingEmploymentEntity != null) {
                BeanUtils.copyProperties(updateEmploymentDetailsDTO, existingEmploymentEntity);

                // Update JobHistoryEntity
                JobHistoryEntity jobHistoryEntity = humanResourceService.findByJobHistoryId(updateEmploymentDetailsDTO.getJobHistoryId());
                if (jobHistoryEntity != null) {
                    BeanUtils.copyProperties(updateEmploymentDetailsDTO, jobHistoryEntity);
                    jobHistoryEntity.setEmployment(existingEmploymentEntity);
                    humanResourceService.updateJobHistory(jobHistoryEntity);
                }

                // Update EmploymentWorkingTimeEntity
                EmploymentWorkingTimeEntity employmentWorkingTimeEntity = humanResourceService.findByEmploymentWorkingTimeId(updateEmploymentDetailsDTO.getEmploymentWorkingTimeId());
                if (employmentWorkingTimeEntity != null) {
                    BeanUtils.copyProperties(updateEmploymentDetailsDTO, employmentWorkingTimeEntity);
                    employmentWorkingTimeEntity.setEmployment(existingEmploymentEntity);
                    humanResourceService.updateEmploymentWorkingTime(employmentWorkingTimeEntity);
                }
                return "Update Successfully";
            }
            else return "Cannot find EmploymentEntity with id: " + updateEmploymentDetailsDTO.getEmploymentId();
        }
        return "No employment details found";
    }


    public MergePersonDTO mergePersonBuilder(EmployeeEntity employeeEntity, PersonalEntity personalEntity){
        return MergePersonDTO.builder().personalId(personalEntity.getPersonalId())
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
