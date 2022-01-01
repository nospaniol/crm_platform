package com.crm.service.impl;

import com.crm.model.Nationality;
import com.crm.repository.NationalityRepository;
import com.crm.service.NationalityService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NationalityServiceImpl implements NationalityService {

    @Autowired
    private NationalityRepository bizRepository;

    @Override
    public Nationality save(Nationality entity) {
        return bizRepository.save(entity);
    }

    @Override
    public Nationality update(Nationality entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(Nationality entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public Nationality find(Long id) {
        Optional<Nationality> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<Nationality> findAll() {
        return (List<Nationality>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<Nationality> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public Nationality findByNationality(String nationality) {
        return bizRepository.findByNationality(nationality);
    }

    @Override
    public Nationality findByNation(String nation) {
        return bizRepository.findByNation(nation);
    }

}
