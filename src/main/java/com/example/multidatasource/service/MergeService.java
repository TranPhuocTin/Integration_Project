package com.example.multidatasource.service;

import com.example.multidatasource.entity.sqlsever.EmploymentEntity;
import com.example.multidatasource.entity.sqlsever.EmploymentWorkingTimeEntity;
import com.example.multidatasource.entity.sqlsever.JobHistoryEntity;
import com.example.multidatasource.payload.MergePersonDTO;
import com.example.multidatasource.entity.mysql.PayRateEntity;
import com.example.multidatasource.entity.sqlsever.BenefitPlanEntity;
import com.example.multidatasource.payload.UpdateEmploymentDetails;

import java.util.List;

public interface MergeService {
    List<MergePersonDTO> mergeEmployeePersonal();
    boolean updateEmployeePersonal(MergePersonDTO mergePersonDTO, int id);
    String deleteEmployeePersonal(int id);
    MergePersonDTO getMergePersonById(int id);
    boolean updateBenefitPlanPayrate(int id, BenefitPlanEntity benefitPlan, PayRateEntity payrate, double paidToDate, double paidLastYear);
    String updateEmploymentDetails(int id, UpdateEmploymentDetails updateEmploymentDetails);
}