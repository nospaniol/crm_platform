package com.crm.service;

import com.crm.model.Axle;
import com.crm.generic.GenericService;

public interface AxleService extends GenericService<Axle> {

    public Axle findByAxleName(String name);

    public Axle findByAxleId(Long id);

}
