package com.crm.repository;

import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.DepartmentAccount;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "departmentAccounts", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "departmentAccounts", key = "#root.target.REDIS_KEY")
public interface DepartmentAccountRepository extends MongoRepository<DepartmentAccount, Long> {

    public static final String REDIS_KEY = "departmentAccounttokens";

    public long countByClientProfile(ClientProfile profile);

    public DepartmentAccount findByAccountId(Long id);

    public DepartmentAccount findByClientProfileAndDepartment(ClientProfile profile, Department department);

    public DepartmentAccount findByDepartment(Department department);

    public Page<DepartmentAccount> findByClientProfile(Pageable pageable, ClientProfile profile);

    public List<DepartmentAccount> findByClientProfile(ClientProfile profile);

}
