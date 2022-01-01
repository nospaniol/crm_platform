package com.crm.service.impl;

import com.crm.model.EmailToken;
import com.crm.repository.EmailTokenRepository;
import com.crm.service.EmailTokenService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailTokenServiceImpl implements EmailTokenService {

    @Autowired
    private EmailTokenRepository bizRepository;

    @Override
    public EmailToken save(EmailToken entity) {
        return bizRepository.save(entity);
    }

    @Override
    public EmailToken update(EmailToken entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(EmailToken entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public EmailToken find(Long id) {
        Optional<EmailToken> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<EmailToken> findAll() {
        return (List<EmailToken>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<EmailToken> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public EmailToken findByEmailAddress(String emailAddress) {
        return bizRepository.findByEmailAddress(emailAddress);
    }

}
