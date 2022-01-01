package com.crm.repository;

import com.crm.model.County;
import com.crm.model.Region;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "regions", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "regions", key = "#root.target.REDIS_KEY")//("regionRepository")
public interface RegionRepository extends MongoRepository<Region, Long> {

    public static final String REDIS_KEY = "regiontokens";

    Region findByRegionId(Long regionId);

    Region findByRegionNameAndCounty(String region, County county);

    List<Region> findByRegionName(String region);

    List<Region> findByCounty(County county);
}
