package com.crm.service.impl;

import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.DepartmentAccount;
import com.crm.repository.DepartmentAccountRepository;
import com.crm.service.DepartmentAccountService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DepartmentAccountServiceImpl implements DepartmentAccountService {

    @Autowired
    private DepartmentAccountRepository bizRepository;

    @Override
    public DepartmentAccount save(DepartmentAccount entity) {
        return bizRepository.save(entity);
    }

    @Override
    public DepartmentAccount update(DepartmentAccount entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(DepartmentAccount entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public DepartmentAccount find(Long id) {
        Optional<DepartmentAccount> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<DepartmentAccount> findAll() {
        return (List<DepartmentAccount>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<DepartmentAccount> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public DepartmentAccount findByAccountId(Long id) {
        return bizRepository.findByAccountId(id);
    }

    @Override
    public Page<DepartmentAccount> findByClientProfile(Pageable pageable, ClientProfile profile) {
        return bizRepository.findByClientProfile(pageable, profile);
    }

    @Override
    public long countByClientProfile(ClientProfile profile) {
        return bizRepository.countByClientProfile(profile);
    }

    @Override
    public DepartmentAccount findByClientProfileAndDepartment(ClientProfile profile, Department department) {
        return bizRepository.findByClientProfileAndDepartment(profile, department);
    }

    @Override
    public Page<DepartmentAccount> findAll(Pageable pageable) {
        return bizRepository.findAll(pageable);
    }

    @Override
    public List<DepartmentAccount> findByClientProfile(ClientProfile profile) {
        return bizRepository.findByClientProfile(profile);
    }

    @Override
    public DepartmentAccount findByDepartment(Department department) {
        return bizRepository.findByDepartment(department);
    }

}
