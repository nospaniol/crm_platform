package com.crm.repository;

import com.crm.model.Sessions;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "sessions", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "sessions", key = "#root.target.REDIS_KEY")//
public interface SessionRepository extends MongoRepository<Sessions, Long> {

    public static final String REDIS_KEY = "sessionstokens";

    Sessions findBySessionName(String sessionName);

    // Session findTopBySessionNameOrderByIdDesc();
}
