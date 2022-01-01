package com.crm.service;

import com.crm.generic.GenericService;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.DepartmentAccount;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DepartmentAccountService extends GenericService<DepartmentAccount> {

    public long countByClientProfile(ClientProfile profile);

    public DepartmentAccount findByAccountId(Long id);

    public DepartmentAccount findByClientProfileAndDepartment(ClientProfile profile, Department department);

    public DepartmentAccount findByDepartment(Department department);

    public Page<DepartmentAccount> findByClientProfile(Pageable pageable, ClientProfile profile);

    public List<DepartmentAccount> findByClientProfile(ClientProfile profile);

    public Page<DepartmentAccount> findAll(Pageable pageable);

}
