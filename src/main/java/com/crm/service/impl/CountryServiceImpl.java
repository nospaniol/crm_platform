package com.crm.service.impl;

import com.crm.model.Country;
import com.crm.repository.CountryRepository;
import com.crm.service.CountryService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository bizRepository;

    @Override
    public Country save(Country entity) {
        return bizRepository.save(entity);
    }

    @Override
    public Country update(Country entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(Country entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public Country find(Long id) {
        Optional<Country> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<Country> findAll() {
        return (List<Country>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<Country> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public Country findByCountryName(String countryName) {
        return bizRepository.findByCountryName(countryName);
    }

    @Override
    public Country findByCountryId(Long countryId) {
        return bizRepository.findByCountryId(countryId);
    }

}
