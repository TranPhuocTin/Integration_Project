package com.example.multidatasource.repository.hrm_repo;

import com.example.multidatasource.entity.sqlsever.EmploymentEntity;
import com.example.multidatasource.entity.sqlsever.JobHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobHistoryRepository extends JpaRepository<JobHistoryEntity, Integer> {
    void deleteByEmployment(EmploymentEntity employment);

    List<JobHistoryEntity> findByEmployment_Personal(int personalId);
    List<JobHistoryEntity> findAllBy();

    JobHistoryEntity findByJobHistoryId(Long id);
}
