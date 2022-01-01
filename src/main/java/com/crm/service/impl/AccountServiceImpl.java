package com.crm.service.impl;

import com.crm.model.ClientProfile;
import com.crm.model.Account;
import com.crm.repository.AccountRepository;
import com.crm.service.AccountService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository bizRepository;

    @Override
    public Account save(Account entity) {
        return bizRepository.save(entity);
    }

    @Override
    public Account update(Account entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(Account entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public Account find(Long id) {
        Optional<Account> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<Account> findAll() {
        return (List<Account>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<Account> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public Account findByAccountId(Long id) {
        return bizRepository.findByAccountId(id);
    }

    @Override
    public long countByClientProfile(ClientProfile profile) {
        return bizRepository.countByClientProfile(profile);
    }

    @Override
    public Page<Account> findAll(Pageable pageable) {
        return bizRepository.findAll(pageable);
    }

    @Override
    public Account findByClientProfile(ClientProfile profile) {
        return bizRepository.findByClientProfile(profile);
    }

}
