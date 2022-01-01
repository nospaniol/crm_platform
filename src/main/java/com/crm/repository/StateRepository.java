package com.crm.repository;

import com.crm.model.State;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "states", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "states", key = "#root.target.REDIS_KEY")
public interface StateRepository extends MongoRepository<State, Long> {

    public static final String REDIS_KEY = "statetokens";

    public State findByStateName(String name);

    public State findByStateId(Long id);
}
