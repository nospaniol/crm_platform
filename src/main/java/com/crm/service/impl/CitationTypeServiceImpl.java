package com.crm.service.impl;

import com.crm.model.CitationType;
import com.crm.repository.CitationTypeRepository;
import com.crm.service.CitationTypeService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CitationTypeServiceImpl implements CitationTypeService {

    @Autowired
    private CitationTypeRepository bizRepository;

    @Override
    public CitationType save(CitationType entity) {
        return bizRepository.save(entity);
    }

    @Override
    public CitationType update(CitationType entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(CitationType entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public CitationType find(Long id) {
        Optional<CitationType> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<CitationType> findAll() {
        return (List<CitationType>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<CitationType> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public CitationType findByCitationTypeName(String name) {
        return bizRepository.findByCitationTypeName(name);
    }

    @Override
    public CitationType findByCitationTypeId(Long id) {
        return bizRepository.findByCitationTypeId(id);
    }

}
