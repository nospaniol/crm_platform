package com.crm.repository;

import com.crm.model.ClientProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "clientProfiles", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "clientProfiles", key = "#root.target.REDIS_KEY")
public interface ClientProfileRepository extends MongoRepository<ClientProfile, Long> {

    public static final String REDIS_KEY = "clientProfiletokens";

    ClientProfile findByClientProfileId(Long id);

    ClientProfile findByCompanyName(String companyName);

}
