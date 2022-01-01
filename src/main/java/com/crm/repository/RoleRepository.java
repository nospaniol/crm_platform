package com.crm.repository;

import com.crm.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "roles", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "roles", key = "#root.target.REDIS_KEY")//("roleRepository")
public interface RoleRepository extends MongoRepository<Role, Long> {

    public static final String REDIS_KEY = "roletokens";

    Role findByName(String role);

}
