package com.crm.service;

import com.crm.generic.GenericService;
import com.crm.model.ClientProfile;
import com.crm.model.Saving;
import com.crm.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SavingService extends GenericService<Saving> {

    public long countByClientProfile(ClientProfile profile);

    public Saving findBySavingId(Long id);

    public Saving findByClientProfile(ClientProfile profile);

    public Page<Saving> findAll(Pageable pageable);

}
