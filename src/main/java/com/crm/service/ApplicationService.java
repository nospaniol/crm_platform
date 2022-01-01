package com.crm.service;

import com.crm.generic.GenericService;
import com.crm.model.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApplicationService extends GenericService<Application> {

    public Application findByCompanyName(String companyName);

    public Application findByApplicationId(Long id);

    public Page<Application> findAll(Pageable pageable);

    public long countAll();
}
