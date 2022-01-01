package com.crm.repository;

import com.crm.model.Nationality;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "nationalitys", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "nationalitys", key = "#root.target.REDIS_KEY")//("nationalityRepository")
public interface NationalityRepository extends MongoRepository<Nationality, Long> {

    public static final String REDIS_KEY = "nationalitytokens";

    Nationality findByNationality(String nationality);

    Nationality findByNation(String nation);
}
