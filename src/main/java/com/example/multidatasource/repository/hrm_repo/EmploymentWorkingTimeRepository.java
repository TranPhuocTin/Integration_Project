package com.example.multidatasource.repository.hrm_repo;

import com.example.multidatasource.entity.mysql.EmployeeEntity;
import com.example.multidatasource.entity.sqlsever.EmploymentEntity;
import com.example.multidatasource.entity.sqlsever.EmploymentWorkingTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmploymentWorkingTimeRepository extends JpaRepository<EmploymentWorkingTimeEntity, Integer>{
    void deleteByEmployment(EmploymentEntity employment);

    List<EmploymentWorkingTimeEntity> findByEmployment_Personal(int personalId);

    List<EmploymentWorkingTimeEntity> findAllBy();
}
