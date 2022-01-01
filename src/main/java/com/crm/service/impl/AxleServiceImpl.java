package com.crm.service.impl;

import com.crm.model.Axle;
import com.crm.repository.AxleRepository;
import com.crm.service.AxleService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class AxleServiceImpl implements AxleService {

    @Autowired
    private AxleRepository bizRepository;

    @Override
    public Axle save(Axle entity) {
        return bizRepository.save(entity);
    }

    @Override
    public Axle update(Axle entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(Axle entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public Axle find(Long id) {
        Optional<Axle> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<Axle> findAll() {
        return (List<Axle>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<Axle> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public Axle findByAxleName(String name) {
        return bizRepository.findByAxleName(name);
    }

    @Override
    public Axle findByAxleId(Long id) {
        return bizRepository.findByAxleId(id);
    }

}
