package com.crm.service.impl;

import com.crm.model.Blacklist;
import com.crm.repository.BlacklistRepository;
import com.crm.service.BlacklistService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class BlacklistServiceImpl implements BlacklistService {

    @Autowired
    private BlacklistRepository bizRepository;

    @Override
    public Blacklist save(Blacklist entity) {
        return bizRepository.save(entity);
    }

    @Override
    public Blacklist update(Blacklist entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(Blacklist entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public Blacklist find(Long id) {
        Optional<Blacklist> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<Blacklist> findAll() {
        return (List<Blacklist>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<Blacklist> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public Blacklist findByEmail(String nationalId) {
        return bizRepository.findByEmail(nationalId);
    }

}
