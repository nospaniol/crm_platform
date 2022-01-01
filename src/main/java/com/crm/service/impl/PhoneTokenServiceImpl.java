package com.crm.service.impl;

import com.crm.model.PhoneToken;
import com.crm.repository.PhoneTokenRepository;
import com.crm.service.PhoneTokenService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneTokenServiceImpl implements PhoneTokenService {

    @Autowired
    private PhoneTokenRepository bizRepository;

    @Override
    public PhoneToken save(PhoneToken entity) {
        return bizRepository.save(entity);
    }

    @Override
    public PhoneToken update(PhoneToken entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(PhoneToken entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public PhoneToken find(Long id) {
        Optional<PhoneToken> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<PhoneToken> findAll() {
        return (List<PhoneToken>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<PhoneToken> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public PhoneToken findByPhoneNumber(String phoneNumber) {
        return bizRepository.findByPhoneNumber(phoneNumber);
    }

}
