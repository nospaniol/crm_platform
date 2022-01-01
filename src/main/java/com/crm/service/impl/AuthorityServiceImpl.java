package com.crm.service.impl;

import com.crm.model.Authority;
import com.crm.repository.AuthorityRepository;
import com.crm.service.AuthorityService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository bizRepository;

    @Override
    public Authority save(Authority entity) {
        return bizRepository.save(entity);
    }

    @Override
    public Authority update(Authority entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(Authority entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public Authority find(Long id) {
        Optional<Authority> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<Authority> findAll() {
        return (List<Authority>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<Authority> entities) {
        bizRepository.deleteAll(entities);
    }

    @Override
    public Authority findByRole(String role) {
        return bizRepository.findByRole(role);
    }
}
