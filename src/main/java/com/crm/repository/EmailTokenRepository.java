package com.crm.repository;

import com.crm.model.EmailToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "email_token", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "email_token", key = "#root.target.REDIS_KEY")
public interface EmailTokenRepository extends MongoRepository<EmailToken, Long> {

    public static final String REDIS_KEY = "emailtokens";

    EmailToken findByEmailAddress(String emailAddress);

}
