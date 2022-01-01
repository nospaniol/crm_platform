package com.crm.service.impl;

import com.crm.model.StoreLocation;
import com.crm.repository.StoreLocationRepository;
import com.crm.service.StoreLocationService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreLocationServiceImpl implements StoreLocationService {

    @Autowired
    private StoreLocationRepository bizRepository;

    @Override
    public StoreLocation save(StoreLocation entity) {
        return bizRepository.save(entity);
    }

    @Override
    public StoreLocation update(StoreLocation entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(StoreLocation entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public StoreLocation find(Long id) {
        Optional<StoreLocation> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<StoreLocation> findAll() {
        return (List<StoreLocation>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<StoreLocation> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public StoreLocation findByStoreLocationName(String name) {
        return bizRepository.findByStoreLocationName(name);
    }

    @Override
    public StoreLocation findByStoreLocationId(Long id) {
        return bizRepository.findByStoreLocationId(id);
    }

}
