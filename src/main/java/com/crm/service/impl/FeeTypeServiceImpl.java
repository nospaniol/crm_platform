package com.crm.service.impl;

import com.crm.model.FeeType;
import com.crm.repository.FeeTypeRepository;
import com.crm.service.FeeTypeService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeeTypeServiceImpl implements FeeTypeService {

    @Autowired
    private FeeTypeRepository bizRepository;

    @Override
    public FeeType save(FeeType entity) {
        return bizRepository.save(entity);
    }

    @Override
    public FeeType update(FeeType entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(FeeType entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public FeeType find(Long id) {
        Optional<FeeType> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<FeeType> findAll() {
        return (List<FeeType>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<FeeType> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public FeeType findByFeeTypeName(String name) {
        return bizRepository.findByFeeTypeName(name);
    }

    @Override
    public FeeType findByFeeTypeId(Long id) {
        return bizRepository.findByFeeTypeId(id);
    }

}
