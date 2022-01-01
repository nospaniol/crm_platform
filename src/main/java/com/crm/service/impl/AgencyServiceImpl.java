package com.crm.service.impl;

import com.crm.model.Agency;
import com.crm.repository.AgencyRepository;
import com.crm.service.AgencyService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgencyServiceImpl implements AgencyService {

    @Autowired
    private AgencyRepository bizRepository;

    @Override
    public Agency save(Agency entity) {
        return bizRepository.save(entity);
    }

    @Override
    public Agency update(Agency entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(Agency entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public Agency find(Long id) {
        Optional<Agency> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<Agency> findAll() {
        return (List<Agency>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<Agency> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public Agency findByAgencyName(String agencyName) {
        return bizRepository.findByAgencyName(agencyName);
    }

    @Override
    public Agency findByAgencyId(Long agencyId) {
        return bizRepository.findByAgencyId(agencyId);
    }

}
