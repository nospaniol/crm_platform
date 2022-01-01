package com.crm.service.impl;

import com.crm.model.County;
import com.crm.model.Region;
import com.crm.repository.RegionRepository;
import com.crm.service.RegionService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    private RegionRepository bizRepository;

    @Override
    public Region save(Region entity) {
        return bizRepository.save(entity);
    }

    @Override
    public Region update(Region entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(Region entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public Region find(Long id) {
        Optional<Region> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<Region> findAll() {
        return (List<Region>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<Region> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public List<Region> findByRegionName(String county) {
        return bizRepository.findByRegionName(county);
    }

    @Override
    public Region findByRegionId(Long countyId) {
        return bizRepository.findByRegionId(countyId);
    }

    @Override
    public Region findByRegionNameAndCounty(String county, County country) {
        return bizRepository.findByRegionNameAndCounty(county, country);
    }

    @Override
    public List<Region> findByCounty(County county) {
        return bizRepository.findByCounty(county);
    }

}
