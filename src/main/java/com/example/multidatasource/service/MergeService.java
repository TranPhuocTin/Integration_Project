package com.example.multidatasource.service;

import com.example.multidatasource.entity.sqlsever.BenefitPlanEntity;
import com.example.multidatasource.payload.MergePersonDTO;
import com.example.multidatasource.payload.UpdateBenefitAndPayRateDTO;
import com.example.multidatasource.payload.UpdateEmploymentDetailsDTO;

import java.util.List;

public interface MergeService {
    List<MergePersonDTO> mergeEmployeePersonal();
    boolean updateEmployeePersonal(MergePersonDTO mergePersonDTO, int id);
    String deleteEmployeePersonal(int id);
    MergePersonDTO getMergePersonById(int id);
    boolean updateBenefitPlanPayrate(int id, UpdateBenefitAndPayRateDTO updateBenefitAndPayrateDTO);
    String updateEmploymentDetails(int id, UpdateEmploymentDetailsDTO updateEmploymentDetailsDTO);
    void writeBenefitPlanChangesToFile(int personalId, UpdateBenefitAndPayRateDTO updateBenefitAndPayrateDTO, BenefitPlanEntity oldBenefitPlan);
    List<MergePersonDTO> readBenefitPlanChangesFromFile(List<MergePersonDTO> mergePersonDTOList);
}