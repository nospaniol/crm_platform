package com.crm.service.impl;

import com.crm.model.Sessions;
import com.crm.repository.SessionRepository;
import com.crm.service.SessionService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository bizRepository;

    public void setBizRepository(SessionRepository bizRepository) {
        this.bizRepository = bizRepository;
    }

    @Override
    public Sessions save(Sessions entity) {
        return bizRepository.save(entity);
    }

    @Override
    public Sessions update(Sessions entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(Sessions entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public Sessions find(Long id) {
        Optional<Sessions> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<Sessions> findAll() {
        return (List<Sessions>) bizRepository.findAll();
    }

    @Override
    public Sessions findBySessionName(String categoryName) {
        return bizRepository.findBySessionName(categoryName);
    }

    @Override
    public void deleteAll(List<Sessions> categories) {
        bizRepository.deleteAll(categories);
    }

    @Override
    public Sessions findBySessionLatest() {
        return (Sessions) bizRepository.findAll();
    }

}
