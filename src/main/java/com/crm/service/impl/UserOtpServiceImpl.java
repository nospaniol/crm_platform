package com.crm.service.impl;

import com.crm.model.User;
import com.crm.model.UserOtp;
import com.crm.repository.UserOtpRepository;
import com.crm.service.UserOtpService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserOtpServiceImpl implements UserOtpService {

    @Autowired
    private UserOtpRepository bizRepository;

    @Override
    public UserOtp save(UserOtp entity) {
        return bizRepository.save(entity);
    }

    @Override
    public UserOtp update(UserOtp entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(UserOtp entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public UserOtp find(Long id) {
        Optional<UserOtp> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<UserOtp> findAll() {
        return (List<UserOtp>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<UserOtp> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public UserOtp findByUser(User user) {
        return bizRepository.findByUser(user);
    }

}
