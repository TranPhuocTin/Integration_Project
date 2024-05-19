package com.example.multidatasource.repository.pr_repo;

import com.example.multidatasource.entity.mysql.EmployeeEntity;
import com.example.multidatasource.entity.mysql.PayRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {
}
