package com.crm.service.impl;

import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.DepartmentSaving;
import com.crm.repository.DepartmentSavingRepository;
import com.crm.service.DepartmentSavingService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DepartmentSavingServiceImpl implements DepartmentSavingService {

    @Autowired
    private DepartmentSavingRepository bizRepository;

    @Override
    public DepartmentSaving save(DepartmentSaving entity) {
        return bizRepository.save(entity);
    }

    @Override
    public DepartmentSaving update(DepartmentSaving entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(DepartmentSaving entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public DepartmentSaving find(Long id) {
        Optional<DepartmentSaving> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<DepartmentSaving> findAll() {
        return (List<DepartmentSaving>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<DepartmentSaving> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public DepartmentSaving findBySavingId(Long id) {
        return bizRepository.findBySavingId(id);
    }

    @Override
    public Page<DepartmentSaving> findByClientProfile(Pageable pageable, ClientProfile profile) {
        return bizRepository.findByClientProfile(pageable, profile);
    }

    @Override
    public long countByClientProfile(ClientProfile profile) {
        return bizRepository.countByClientProfile(profile);
    }

    @Override
    public DepartmentSaving findByClientProfileAndDepartment(ClientProfile profile, Department department) {
        return bizRepository.findByClientProfileAndDepartment(profile, department);
    }

    @Override
    public Page<DepartmentSaving> findAll(Pageable pageable) {
        return bizRepository.findAll(pageable);
    }

    @Override
    public List<DepartmentSaving> findByClientProfile(ClientProfile profile) {
        return bizRepository.findByClientProfile(profile);
    }

    @Override
    public DepartmentSaving findByDepartment(Department department) {
        return bizRepository.findByDepartment(department);
    }

}
