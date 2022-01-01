package com.crm.repository;

import com.crm.model.TagType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "tagTypes", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "tagTypes", key = "#root.target.REDIS_KEY")
public interface TagTypeRepository extends MongoRepository<TagType, Long> {

    public static final String REDIS_KEY = "tagTypetokens";

    public TagType findByTagTypeName(String name);

    public TagType findByTagTypeId(Long id);
}
