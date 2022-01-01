package com.crm.repository;

import com.crm.model.Department;
import com.crm.model.SubDepartment;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubDepartmentRepository extends MongoRepository<SubDepartment, Long> {

    public long countByDepartment(Department profile);

    SubDepartment findByDepartmentId(Long id);

    SubDepartment findByDepartmentNameAndDepartment(String departmentName, Department profile);

    List<SubDepartment> findByDepartment(Department profile);

    SubDepartment findByDepartmentName(String departmentName);

}
