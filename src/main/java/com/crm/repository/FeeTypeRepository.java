package com.crm.repository;

import com.crm.model.FeeType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "feeTypes", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "feeTypes", key = "#root.target.REDIS_KEY")
public interface FeeTypeRepository extends MongoRepository<FeeType, Long> {

    public static final String REDIS_KEY = "feeTypetokens";

    public FeeType findByFeeTypeName(String name);

    public FeeType findByFeeTypeId(Long id);
}
