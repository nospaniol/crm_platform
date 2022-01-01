package com.crm.repository;

import com.crm.model.Country;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "countries", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "countries", key = "#root.target.REDIS_KEY")//("countryRepository")
public interface CountryRepository extends MongoRepository<Country, Long> {

    public static final String REDIS_KEY = "countrytokens";

    Country findByCountryId(Long countryId);

    Country findByCountryName(String countryName);
}
