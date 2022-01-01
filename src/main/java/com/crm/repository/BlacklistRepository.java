package com.crm.repository;

import com.crm.model.Blacklist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "blacklists", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "blacklists", key = "#root.target.REDIS_KEY")
public interface BlacklistRepository extends MongoRepository<Blacklist, Long> {

    public static final String REDIS_KEY = "blacklisttokens";

    Blacklist findByEmail(String blacklist);

}
