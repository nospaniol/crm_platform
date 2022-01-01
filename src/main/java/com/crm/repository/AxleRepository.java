package com.crm.repository;

import com.crm.model.Axle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "axles", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "axles", key = "#root.target.REDIS_KEY")
public interface AxleRepository extends MongoRepository<Axle, Long> {

    public static final String REDIS_KEY = "axletokens";

    public Axle findByAxleName(String name);

    public Axle findByAxleId(Long id);
}
