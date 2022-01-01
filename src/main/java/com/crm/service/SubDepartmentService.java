package com.crm.service;

import com.crm.generic.GenericService;
import com.crm.model.Department;
import com.crm.model.SubDepartment;
import java.util.List;

public interface SubDepartmentService extends GenericService<SubDepartment> {

    public long countByDepartment(Department profile);

    SubDepartment findByDepartmentId(Long id);

    SubDepartment findByDepartmentNameAndDepartment(String departmentName, Department profile);

    List<SubDepartment> findByDepartment(Department profile);

    SubDepartment findByDepartmentName(String departmentName);

}
