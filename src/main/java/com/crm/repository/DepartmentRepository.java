package com.crm.repository;

import com.crm.model.ClientProfile;
import com.crm.model.Department;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "departments", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "departments", key = "#root.target.REDIS_KEY")
public interface DepartmentRepository extends MongoRepository<Department, Long> {

    public static final String REDIS_KEY = "departmenttokens";

    public long countByClientProfile(ClientProfile profile);

    Department findByDepartmentId(Long id);

    Department findByDepartmentNameAndClientProfile(String departmentName, ClientProfile profile);

    List<Department> findByClientProfile(ClientProfile profile);

    Department findByDepartmentName(String departmentName);
}
