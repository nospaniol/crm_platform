package com.crm.service.impl;

import com.crm.model.ClientProfile;
import com.crm.model.Saving;
import com.crm.repository.SavingRepository;
import com.crm.service.SavingService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SavingServiceImpl implements SavingService {

    @Autowired
    private SavingRepository bizRepository;

    @Override
    public Saving save(Saving entity) {
        return bizRepository.save(entity);
    }

    @Override
    public Saving update(Saving entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(Saving entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public Saving find(Long id) {
        Optional<Saving> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<Saving> findAll() {
        return (List<Saving>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<Saving> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public Saving findBySavingId(Long id) {
        return bizRepository.findBySavingId(id);
    }

    @Override
    public long countByClientProfile(ClientProfile profile) {
        return bizRepository.countByClientProfile(profile);
    }

    @Override
    public Page<Saving> findAll(Pageable pageable) {
        return bizRepository.findAll(pageable);
    }

    @Override
    public Saving findByClientProfile(ClientProfile profile) {
        return bizRepository.findByClientProfile(profile);
    }

}
