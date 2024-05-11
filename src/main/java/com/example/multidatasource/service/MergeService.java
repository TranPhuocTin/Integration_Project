package com.example.multidatasource.service;

import com.example.multidatasource.entity.merge.MergePerson;
import com.example.multidatasource.entity.mysql.PayRateEntity;
import com.example.multidatasource.entity.sqlsever.BenefitPlanEntity;

import java.util.List;

public interface MergeService {
    List<MergePerson> mergeEmployeePersonal();
    boolean updateEmployeePersonal(MergePerson mergePerson, int id);
    String deleteEmployeePersonal(int id);
    MergePerson getMergePersonById(int id);
    boolean updateBenefitPlanPayrate(int id, BenefitPlanEntity benefitPlan, PayRateEntity payrate, double paidToDate, double paidLastYear);
}