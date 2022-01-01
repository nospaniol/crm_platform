package com.crm.repository;

import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.DepartmentSaving;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "departmentSavings", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "departmentSavings", key = "#root.target.REDIS_KEY")
public interface DepartmentSavingRepository extends MongoRepository<DepartmentSaving, Long> {

    public static final String REDIS_KEY = "departmentSavingtokens";

    public long countByClientProfile(ClientProfile profile);

    public DepartmentSaving findBySavingId(Long id);

    public DepartmentSaving findByClientProfileAndDepartment(ClientProfile profile, Department department);

    public DepartmentSaving findByDepartment(Department department);

    public Page<DepartmentSaving> findByClientProfile(Pageable pageable, ClientProfile profile);

    public List<DepartmentSaving> findByClientProfile(ClientProfile profile);

}
