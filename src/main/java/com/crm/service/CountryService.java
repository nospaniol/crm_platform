package com.crm.service;

import com.crm.generic.GenericService;
import com.crm.model.Country;

public interface CountryService extends GenericService<Country> {

    Country findByCountryId(Long countryId);

    Country findByCountryName(String countryName);
}
