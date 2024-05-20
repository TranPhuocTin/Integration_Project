package com.example.multidatasource.service.imp;

import com.example.multidatasource.entity.sqlsever.*;
import com.example.multidatasource.payload.BenefitPlanChangeDTO;
import com.example.multidatasource.payload.MergePersonDTO;
import com.example.multidatasource.entity.mysql.EmployeeEntity;
import com.example.multidatasource.entity.mysql.PayRateEntity;
import com.example.multidatasource.payload.UpdateBenefitAndPayRateDTO;
import com.example.multidatasource.payload.UpdateEmploymentDetailsDTO;
import com.example.multidatasource.repository.hrm_repo.*;
import com.example.multidatasource.repository.pr_repo.EmployeeRepository;
import com.example.multidatasource.service.PayrollService;
import com.example.multidatasource.service.MergeService;
import com.example.multidatasource.service.HumanResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MergeServiceImp implements MergeService {
    private static final Logger logger = LoggerFactory.getLogger(MergeServiceImp.class);
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

        return readBenefitPlanChangesFromFile(returnValue);
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
        PersonalEntity personalEntity = humanResourceService.getPersonalById(id);

        BenefitPlanEntity benefitPlanUpdate = humanResourceService.findByBenefitPlansId(updateBenefitAndPayrateDTO.getBenefitPlansId());
        PayRateEntity payRateEntityUpdate = payrollService.findByIdPayRates(updateBenefitAndPayrateDTO.getIdPayRates());

        writeBenefitPlanChangesToFile(id, updateBenefitAndPayrateDTO, personalEntity.getBenefitPlan());

        employeeEntity.setPayRates(payRateEntityUpdate);

        personalEntity.setBenefitPlan(benefitPlanUpdate);

        try {
            humanResourceService.updatePersonal(personalEntity);
            payrollService.updateEmployee(employeeEntity);
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

    @Override
    public void writeBenefitPlanChangesToFile(int personalId, UpdateBenefitAndPayRateDTO updateBenefitAndPayrateDTO, BenefitPlanEntity oldBenefitPlan) {
        // Get old benefit plan name
        String oldPlanName = oldBenefitPlan.getPlanName();
        // Get new benefit plan name
        BenefitPlanEntity newBenefitPlan = humanResourceService.findByBenefitPlansId(updateBenefitAndPayrateDTO.getBenefitPlansId());
        String newPlanName = newBenefitPlan.getPlanName();
        // Get current date
        LocalDate localDate = LocalDate.now();
        Date changeDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = formatter.format(changeDate);
        String newLine = personalId + " " + oldPlanName + " " + newPlanName + " " + formattedDate;

        try {
            List<String> lines = new ArrayList<>();
            File file = new File("benefitPlanChanges.txt");
            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    boolean found = false;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(" ");
                        if (Integer.parseInt(parts[0]) == personalId) {
                            lines.add(newLine);
                            found = true;
                        } else {
                            lines.add(line);
                        }
                    }
                    if (!found) {
                        lines.add(newLine);
                    }
                }
            } else {
                lines.add(newLine);
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(file, false))) {
                for (String line : lines) {
                    writer.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<MergePersonDTO> readBenefitPlanChangesFromFile(List<MergePersonDTO> mergePersonDTOList) {
        try (BufferedReader reader = new BufferedReader(new FileReader("benefitPlanChanges.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" "); // Assuming the fields are separated by dashes

                int personalId = Integer.parseInt(parts[0]);
                String oldPlanName = parts[1];
                String newPlanName = parts[2];

                // Assuming the date is the fourth field and is in the format "dd-MM-yyyy"
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                String changeDate = parts[3];

                BenefitPlanChangeDTO benefitPlanChangeDTO = new BenefitPlanChangeDTO(oldPlanName, newPlanName, changeDate);

                MergePersonDTO mergePersonDTO = mergePersonDTOList.stream()
                        .filter(m -> m.getPersonalId() == personalId)
                        .findFirst()
                        .orElse(null);

                if (mergePersonDTO != null) {
                    // If a MergePersonDTO with the same personalId already exists, update its benefitPlanHistory field
                    mergePersonDTO.setBenefitPlanHistory(benefitPlanChangeDTO);
                } else {
                    // If no MergePersonDTO with the same personalId exists, create a new one and add it to the list
                    mergePersonDTO = new MergePersonDTO();
                    mergePersonDTO.setPersonalId(personalId);
                    mergePersonDTO.setBenefitPlanHistory(benefitPlanChangeDTO);
                    mergePersonDTOList.add(mergePersonDTO);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mergePersonDTOList;
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
