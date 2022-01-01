package com.crm.service;

import com.crm.generic.GenericService;
import com.crm.model.Company;

public interface CompanyService extends GenericService<Company> {

    Company findByCompanyName(String name);

    Company findByCompanyId(Long companyId);
}
