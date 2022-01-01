package com.crm.service.impl;

import com.crm.model.Department;
import com.crm.model.SubDepartment;
import com.crm.repository.SubDepartmentRepository;
import com.crm.service.SubDepartmentService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubDepartmentServiceImpl implements SubDepartmentService {

    @Autowired
    private SubDepartmentRepository bizRepository;

    @Override
    public SubDepartment save(SubDepartment entity) {
        return bizRepository.save(entity);
    }

    @Override
    public SubDepartment update(SubDepartment entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(SubDepartment entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public SubDepartment find(Long id) {
        Optional<SubDepartment> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<SubDepartment> findAll() {
        return (List<SubDepartment>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<SubDepartment> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public SubDepartment findByDepartmentId(Long id) {
        return bizRepository.findByDepartmentId(id);
    }

    @Override
    public List<SubDepartment> findByDepartment(Department profile) {
        return bizRepository.findByDepartment(profile);
    }

    @Override
    public SubDepartment findByDepartmentNameAndDepartment(String departmentName, Department profile) {
        return bizRepository.findByDepartmentNameAndDepartment(departmentName.toUpperCase(), profile);
    }

    @Override
    public long countByDepartment(Department profile) {
        return bizRepository.countByDepartment(profile);
    }

    @Override
    public SubDepartment findByDepartmentName(String departmentName) {
        return bizRepository.findByDepartmentName(departmentName);
    }

}
