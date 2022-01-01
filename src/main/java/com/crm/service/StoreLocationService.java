package com.crm.service;

import com.crm.model.StoreLocation;
import com.crm.generic.GenericService;

public interface StoreLocationService extends GenericService<StoreLocation> {

    public StoreLocation findByStoreLocationName(String name);

    public StoreLocation findByStoreLocationId(Long id);

}
