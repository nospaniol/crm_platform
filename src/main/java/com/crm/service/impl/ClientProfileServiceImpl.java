package com.crm.service.impl;

import com.crm.model.ClientProfile;
import com.crm.repository.ClientProfileRepository;
import com.crm.service.ClientProfileService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientProfileServiceImpl implements ClientProfileService {

    @Autowired
    private ClientProfileRepository bizRepository;

    @Override
    public ClientProfile save(ClientProfile entity) {
        return bizRepository.save(entity);
    }

    @Override
    public ClientProfile update(ClientProfile entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(ClientProfile entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public ClientProfile find(Long id) {
        Optional<ClientProfile> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<ClientProfile> findAll() {
        return (List<ClientProfile>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<ClientProfile> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public ClientProfile findByClientProfileId(Long id) {
        return bizRepository.findByClientProfileId(id);
    }

    @Override
    public ClientProfile findByCompanyName(String companyName) {
        return bizRepository.findByCompanyName(companyName.toUpperCase());
    }

    @Override
    public long countAll() {
        return bizRepository.count();
    }

    @Override
    public Page<ClientProfile> findAll(Pageable pageable) {
        return bizRepository.findAll(pageable);
    }

}
