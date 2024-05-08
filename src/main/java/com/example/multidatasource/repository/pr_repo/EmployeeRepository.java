package com.example.multidatasource.repository.pr_repo;

import com.example.multidatasource.entity.mysql.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {
//    @Query(value = "select * from employee", nativeQuery = true)
//    List<EmployeeEntity> getEmployeeInfo();
    void deleteByIdEmployee(int id);
}
