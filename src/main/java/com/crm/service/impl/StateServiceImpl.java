package com.crm.service.impl;

import com.crm.model.State;
import com.crm.repository.StateRepository;
import com.crm.service.StateService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateServiceImpl implements StateService {

    @Autowired
    private StateRepository bizRepository;

    @Override
    public State save(State entity) {
        return bizRepository.save(entity);
    }

    @Override
    public State update(State entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(State entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public State find(Long id) {
        Optional<State> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<State> findAll() {
        return (List<State>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<State> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public State findByStateName(String name) {
        return bizRepository.findByStateName(name);
    }

    @Override
    public State findByStateId(Long id) {
        return bizRepository.findByStateId(id);
    }

}
