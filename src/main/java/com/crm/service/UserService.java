package com.crm.service;

import com.crm.generic.GenericService;
import com.crm.model.Authority;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.User;
import com.crm.model.EmailToken;
import com.crm.model.PhoneToken;
import java.util.List;
//import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends GenericService<User> {

    User findByPhoneToken(PhoneToken phoneToken);

    List<User> findByAuthority(Authority authority);

    User findByEmailToken(EmailToken emailToken);

    User findByUserId(Long UserId);

    List<User> findByClientProfile(ClientProfile profile);

    List<User> findByDepartment(Department department);
}
