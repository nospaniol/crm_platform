package com.crm.service.impl;

import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.repository.DepartmentRepository;
import com.crm.service.DepartmentService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository bizRepository;

    @Override
    public Department save(Department entity) {
        return bizRepository.save(entity);
    }

    @Override
    public Department update(Department entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(Department entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public Department find(Long id) {
        Optional<Department> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<Department> findAll() {
        return (List<Department>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<Department> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public Department findByDepartmentId(Long id) {
        return bizRepository.findByDepartmentId(id);
    }

    @Override
    public List<Department> findByClientProfile(ClientProfile profile) {
        return bizRepository.findByClientProfile(profile);
    }

    @Override
    public Department findByDepartmentNameAndClientProfile(String departmentName, ClientProfile profile) {
        return bizRepository.findByDepartmentNameAndClientProfile(departmentName.toUpperCase(), profile);
    }

    @Override
    public long countByClientProfile(ClientProfile profile) {
        return bizRepository.countByClientProfile(profile);
    }

    @Override
    public Department findByDepartmentName(String departmentName) {
        return bizRepository.findByDepartmentName(departmentName);
    }

}
