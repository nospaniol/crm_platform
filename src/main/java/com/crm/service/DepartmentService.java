package com.crm.service;

import com.crm.generic.GenericService;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import java.util.List;

public interface DepartmentService extends GenericService<Department> {

    public long countByClientProfile(ClientProfile profile);

    Department findByDepartmentId(Long id);

    Department findByDepartmentNameAndClientProfile(String departmentName, ClientProfile profile);

    List<Department> findByClientProfile(ClientProfile profile);

    Department findByDepartmentName(String departmentName);

}
