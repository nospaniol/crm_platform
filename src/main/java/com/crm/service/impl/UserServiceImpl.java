package com.crm.service.impl;

import com.crm.model.Authority;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.crm.model.User;
import com.crm.model.EmailToken;
import com.crm.model.PhoneToken;
import com.crm.repository.EmailTokenRepository;
import com.crm.repository.UserRepository;
import com.crm.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository bizRepository;

    @Autowired
    private EmailTokenRepository emailRepository;

//    
//    @Autowired private BCryptPasswordEncoder passwordEncoder;
    @Override
    public User save(User entity) {
        return bizRepository.save(entity);
    }

    @Override
    //@CachePut(value = "users", key = "#root.target.REDIS_KEY")
    public User update(User entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(User entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public User find(Long id) {
        Optional<User> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    //@Cacheable("users")
    public List<User> findAll() {
        return bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<User> sales) {
        bizRepository.deleteAll(sales);
    }

    @Override
    public User findByPhoneToken(PhoneToken phoneToken) {
        return this.bizRepository.findByPhoneToken(phoneToken);
    }

    @Override
    public User findByEmailToken(EmailToken emailToken) {
        return this.bizRepository.findByEmailToken(emailToken);
    }

    @Override
    public User findByUserId(Long UserId) {
        return this.bizRepository.findByUserId(UserId);
    }

    @Override
    public List<User> findByAuthority(Authority authority) {
        return this.bizRepository.findByAuthority(authority);
    }
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = this.bizRepository.findByEmailToken(this.emailRepository.findByEmailAddress(email));
//        if (user == null) {
//            throw new UsernameNotFoundException("Invalid username or password.");
//        }
//        return new org.springframework.security.core.userdetails.User(user.getEmailToken().getEmailAddress(),
//                user.getPassword(),
//                mapRolesToAuthorities(user.getRoles()));
//    }
//
//    @Autowired private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toList());
//    }

    @Override
    public List<User> findByClientProfile(ClientProfile profile) {
        return bizRepository.findByClientProfile(profile);
    }

    @Override
    public List<User> findByDepartment(Department department) {
        return bizRepository.findByDepartment(department);
    }

}
