package com.crm.repository;

import com.crm.model.Agency;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "agencies", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "agencies", key = "#root.target.REDIS_KEY")
public interface AgencyRepository extends MongoRepository<Agency, Long> {

    public static final String REDIS_KEY = "agencytokens";

    Agency findByAgencyId(Long agencyId);

    Agency findByAgencyName(String agencyName);
}
