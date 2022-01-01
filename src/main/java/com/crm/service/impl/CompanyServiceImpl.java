package com.crm.service.impl;

import com.crm.model.Company;
import com.crm.repository.CompanyRepository;
import com.crm.service.CompanyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository bizRepository;

    @Override
    public Company save(Company entity) {
        return bizRepository.save(entity);
    }

    @Override
    public Company update(Company entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(Company entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public Company find(Long id) {
        Company opdata = bizRepository.findById(id).orElseGet(null);
        return opdata;
    }

    @Override
    public List<Company> findAll() {
        return (List<Company>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<Company> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public Company findByCompanyName(String name) {
        return bizRepository.findByCompanyName(name);
    }

    @Override
    public Company findByCompanyId(Long companyId) {
        return bizRepository.findByCompanyId(companyId);
    }

}
