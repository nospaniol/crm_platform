package com.crm.repository;

import com.crm.model.PhoneToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "phoneTokens", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "phoneTokens", key = "#root.target.REDIS_KEY")//("phoneTokenRepository")
public interface PhoneTokenRepository extends MongoRepository<PhoneToken, Long> {

    public static final String REDIS_KEY = "phoneTokentokens";

    PhoneToken findByPhoneNumber(String phoneNumber);

}
