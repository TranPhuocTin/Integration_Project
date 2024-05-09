package com.example.multidatasource.repository.hrm_repo;

import com.example.multidatasource.entity.sqlsever.EmploymentEntity;
import com.example.multidatasource.entity.sqlsever.PersonalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmploymentRepository extends JpaRepository<EmploymentEntity, Integer> {
}
