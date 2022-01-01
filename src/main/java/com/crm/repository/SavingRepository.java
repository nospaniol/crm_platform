package com.crm.repository;

import com.crm.model.ClientProfile;
import com.crm.model.Saving;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "savings", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "savings", key = "#root.target.REDIS_KEY")
public interface SavingRepository extends MongoRepository<Saving, Long> {

    public static final String REDIS_KEY = "savingtokens";

    public long countByClientProfile(ClientProfile profile);

    public Saving findBySavingId(Long id);

    public Saving findByClientProfile(ClientProfile profile);

}
