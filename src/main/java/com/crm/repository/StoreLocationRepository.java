package com.crm.repository;

import com.crm.model.StoreLocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "storeLocations", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "storeLocations", key = "#root.target.REDIS_KEY")
public interface StoreLocationRepository extends MongoRepository<StoreLocation, Long> {

    public static final String REDIS_KEY = "storeLocationtokens";

    public StoreLocation findByStoreLocationName(String name);

    public StoreLocation findByStoreLocationId(Long id);
}
