package com.crm.service;

import com.crm.model.County;
import com.crm.generic.GenericService;
import com.crm.model.Country;
import java.util.List;

public interface CountyService extends GenericService<County> {

    County findByCountyId(Long countyId);

    County findByCountyNameAndCountry(String county, Country country);

    List<County> findByCountyName(String county);

    List<County> findByCountry(Country county);
}
