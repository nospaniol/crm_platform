package com.crm.service;

import com.crm.model.County;
import com.crm.generic.GenericService;
import com.crm.model.Region;
import java.util.List;

public interface RegionService extends GenericService<Region> {

    Region findByRegionId(Long regionId);

    Region findByRegionNameAndCounty(String region, County county);

    List<Region> findByRegionName(String region);

    List<Region> findByCounty(County county);
}
