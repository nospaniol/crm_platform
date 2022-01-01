package com.crm.repository;

import com.crm.model.Authority;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.crm.model.User;
import com.crm.model.EmailToken;
import com.crm.model.PhoneToken;
import java.util.List;

@Repository
//@Cacheable(value = "users", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "users", key = "#root.target.REDIS_KEY")
public interface UserRepository extends MongoRepository<User, Long> {

    User findByPhoneToken(PhoneToken phoneToken);

    User findByEmailToken(EmailToken emailToken);

    User findByUserId(Long UserId);

    List<User> findByAuthority(Authority authority);

    List<User> findByClientProfile(ClientProfile profile);

    List<User> findByDepartment(Department department);
}
