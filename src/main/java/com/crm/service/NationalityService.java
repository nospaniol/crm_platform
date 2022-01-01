package com.crm.service;

import com.crm.model.Nationality;
import com.crm.generic.GenericService;

public interface NationalityService extends GenericService<Nationality> {

    Nationality findByNationality(String nationality);

    Nationality findByNation(String nation);
}
