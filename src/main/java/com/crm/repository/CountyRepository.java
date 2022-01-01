package com.crm.repository;

import com.crm.model.Country;
import com.crm.model.County;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "counties", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "counties", key = "#root.target.REDIS_KEY")
public interface CountyRepository extends MongoRepository<County, Long> {

    public static final String REDIS_KEY = "countytokens";

    County findByCountyId(Long countyId);

    County findByCountyNameAndCountry(String county, Country country);

    List<County> findByCountyName(String county);

    List<County> findByCountry(Country county);
}
