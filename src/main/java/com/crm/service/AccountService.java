package com.crm.service;

import com.crm.generic.GenericService;
import com.crm.model.ClientProfile;
import com.crm.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService extends GenericService<Account> {

    public long countByClientProfile(ClientProfile profile);

    public Account findByAccountId(Long id);

    public Account findByClientProfile(ClientProfile profile);

    public Page<Account> findAll(Pageable pageable);

}
