package com.crm.repository;

import com.crm.model.ClientProfile;
import com.crm.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account, Long> {

    public static final String REDIS_KEY = "accounttokens";

    public long countByClientProfile(ClientProfile profile);

    public Account findByAccountId(Long id);

    public Account findByClientProfile(ClientProfile profile);

}
