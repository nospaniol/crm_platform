package com.crm.service.impl;

import com.crm.model.Application;
import com.crm.repository.ApplicationRepository;
import com.crm.service.ApplicationService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository bizRepository;

    @Override
    public Application save(Application entity) {
        return bizRepository.save(entity);
    }

    @Override
    public Application update(Application entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(Application entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public Application find(Long id) {
        Optional<Application> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<Application> findAll() {
        return (List<Application>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<Application> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public Application findByApplicationId(Long id) {
        return bizRepository.findByApplicationId(id);
    }

    @Override
    public Application findByCompanyName(String companyName) {
        return bizRepository.findByCompanyName(companyName.toUpperCase());
    }

    @Override
    public long countAll() {
        return bizRepository.count();
    }

    @Override
    public Page<Application> findAll(Pageable pageable) {
        return bizRepository.findAll(pageable);
    }

}
