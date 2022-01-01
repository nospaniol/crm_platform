package com.crm.repository;

import com.crm.model.CitationType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "citationTypes", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "citationTypes", key = "#root.target.REDIS_KEY")
public interface CitationTypeRepository extends MongoRepository<CitationType, Long> {

    public static final String REDIS_KEY = "citationTypetokens";

    public CitationType findByCitationTypeName(String name);

    public CitationType findByCitationTypeId(Long id);
}
