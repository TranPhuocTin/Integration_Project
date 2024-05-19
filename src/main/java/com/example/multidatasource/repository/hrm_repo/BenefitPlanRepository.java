package com.example.multidatasource.repository.hrm_repo;

import com.example.multidatasource.entity.sqlsever.BenefitPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BenefitPlanRepository extends JpaRepository<BenefitPlanEntity, Integer>{
    BenefitPlanEntity findByBenefitPlansId(int id);
}
