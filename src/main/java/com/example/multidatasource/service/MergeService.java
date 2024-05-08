package com.example.multidatasource.service;

import com.example.multidatasource.entity.merge.MergePerson;

import java.util.List;

public interface MergeService {
    List<MergePerson> mergeEmployeePersonal();
    boolean updateEmployeePersonal(MergePerson mergePerson, int id);
    String deleteEmployeePersonal(int id);
}
