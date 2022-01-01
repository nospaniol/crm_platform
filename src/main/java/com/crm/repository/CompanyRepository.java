package com.crm.repository;

import com.crm.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "companies", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "companies", key = "#root.target.REDIS_KEY")//("companyRepository")
public interface CompanyRepository extends MongoRepository<Company, Long> {

    public static final String REDIS_KEY = "companytokens";

    Company findByCompanyName(String name);

    Company findByCompanyId(Long companyId);

}
