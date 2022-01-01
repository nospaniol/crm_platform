package com.crm.service;

import com.crm.generic.GenericService;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.DepartmentSaving;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartmentSavingService extends GenericService<DepartmentSaving> {

    public long countByClientProfile(ClientProfile profile);

    public DepartmentSaving findBySavingId(Long id);

    public DepartmentSaving findByClientProfileAndDepartment(ClientProfile profile, Department department);

    public DepartmentSaving findByDepartment(Department department);

    public Page<DepartmentSaving> findByClientProfile(Pageable pageable, ClientProfile profile);

    public List<DepartmentSaving> findByClientProfile(ClientProfile profile);

    public Page<DepartmentSaving> findAll(Pageable pageable);

}
