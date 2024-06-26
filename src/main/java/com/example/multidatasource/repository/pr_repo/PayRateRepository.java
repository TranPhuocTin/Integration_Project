package com.example.multidatasource.repository.pr_repo;

import com.example.multidatasource.entity.mysql.PayRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayRateRepository extends JpaRepository<PayRateEntity, Integer> {
    PayRateEntity findByIdPayRates(int id);
}
