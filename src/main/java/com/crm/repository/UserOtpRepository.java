package com.crm.repository;

import com.crm.model.User;
import com.crm.model.UserOtp;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
//@Cacheable(value = "userOtp", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "userOtp", key = "#root.target.REDIS_KEY")//
public interface UserOtpRepository extends MongoRepository<UserOtp, Long> {

    public static final String REDIS_KEY = "userOtptokens";

    UserOtp findByUser(User user);
}
