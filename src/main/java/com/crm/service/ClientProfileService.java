package com.crm.service;

import com.crm.generic.GenericService;
import com.crm.model.User;
import com.crm.model.ClientProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientProfileService extends GenericService<ClientProfile> {

    public ClientProfile findByCompanyName(String companyName);

    public ClientProfile findByClientProfileId(Long id);

    public Page<ClientProfile> findAll(Pageable pageable);

    public long countAll();
}
