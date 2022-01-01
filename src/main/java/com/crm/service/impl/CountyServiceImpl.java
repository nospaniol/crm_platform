package com.crm.service.impl;

import com.crm.model.Country;
import com.crm.model.County;
import com.crm.repository.CountyRepository;
import com.crm.service.CountyService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountyServiceImpl implements CountyService {

    @Autowired
    private CountyRepository bizRepository;

    @Override
    public County save(County entity) {
        return bizRepository.save(entity);
    }

    @Override
    public County update(County entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(County entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public County find(Long id) {
        Optional<County> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<County> findAll() {
        return (List<County>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<County> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public List<County> findByCountyName(String county) {
        return bizRepository.findByCountyName(county);
    }

    @Override
    public County findByCountyId(Long countyId) {
        return bizRepository.findByCountyId(countyId);
    }

    @Override
    public County findByCountyNameAndCountry(String county, Country country) {
        return bizRepository.findByCountyNameAndCountry(county, country);
    }

    @Override
    public List<County> findByCountry(Country county) {
        return bizRepository.findByCountry(county);
    }

}
