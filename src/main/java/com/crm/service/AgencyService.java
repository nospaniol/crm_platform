package com.crm.service;

import com.crm.generic.GenericService;
import com.crm.model.Agency;

public interface AgencyService extends GenericService<Agency> {

    Agency findByAgencyId(Long agencyId);

    Agency findByAgencyName(String agencyName);
}
