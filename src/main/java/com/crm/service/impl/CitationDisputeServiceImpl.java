package com.crm.service.impl;

import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.Citation;
import com.crm.model.CitationDispute;
import com.crm.repository.CitationDisputeRepository;
import com.crm.service.CitationDisputeService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

@Service
public class CitationDisputeServiceImpl implements CitationDisputeService {

    @Autowired
    private CitationDisputeRepository bizRepository;

    @Override
    public CitationDispute save(CitationDispute entity) {
        return bizRepository.save(entity);
    }

    @Override
    public CitationDispute update(CitationDispute entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(CitationDispute entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public CitationDispute find(Long id) {
        Optional<CitationDispute> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<CitationDispute> findAll() {
        return (List<CitationDispute>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<CitationDispute> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public CitationDispute findByDisputeId(Long countryId) {
        return bizRepository.findByDisputeId(countryId);
    }

    @Override
    public long countByClientProfile(ClientProfile profile) {
        return bizRepository.countByClientProfile(profile);
    }

    @Override
    public long countByDepartment(Department department) {
        return bizRepository.countByDepartment(department);
    }

    @Override
    public CitationDispute findByCitation(Citation citation) {
        return bizRepository.findByCitation(citation);
    }

    @Override
    public Page<CitationDispute> findByClientProfile(Pageable pageable, ClientProfile profile) {
        return bizRepository.findByClientProfile(pageable, profile);
    }

    @Override
    public Page<CitationDispute> findByClientProfileAndDisputeStatus(Pageable pageable, ClientProfile profile, String status) {
        return bizRepository.findByClientProfileAndDisputeStatus(pageable, profile, status);
    }

    @Override
    public Page<CitationDispute> findByDepartment(Pageable pageable, Department department) {
        return bizRepository.findByDepartment(pageable, department);
    }

    @Override
    public Page<CitationDispute> findByDepartmentAndDisputeStatus(Pageable pageable, Department department, String status) {
        return bizRepository.findByDepartmentAndDisputeStatus(pageable, department, status);
    }

    @Override
    public List<CitationDispute> findTop10ByOrderByDisputeIdDesc() {
        return bizRepository.findTop10ByOrderByDisputeIdDesc();
    }

    @Override
    public List<CitationDispute> findByDepartmentOrderByDisputeIdDesc(Department department) {
        return bizRepository.findByDepartmentOrderByDisputeIdDesc(department);
    }

    @Override
    public List<CitationDispute> findByClientProfileAndDisputeStatusOrderByDisputeIdDesc(ClientProfile profile, String status) {
        return bizRepository.findByClientProfileAndDisputeStatusOrderByDisputeIdDesc(profile, status);
    }

    @Override
    public List<CitationDispute> findByDepartmentAndDisputeStatusOrderByDisputeIdDesc(Department department, String status) {
        return bizRepository.findByDepartmentAndDisputeStatusOrderByDisputeIdDesc(department, status);
    }

    @Override
    public List<CitationDispute> findByClientProfileOrderByDisputeIdDesc(ClientProfile profile) {
        return bizRepository.findByClientProfileOrderByDisputeIdDesc(profile);
    }

    @Override
    public List<CitationDispute> findTop10ByClientProfileAndDepartmentOrderByDisputeId(ClientProfile profile, Department department) {
        return bizRepository.findTop10ByClientProfileAndDepartmentOrderByDisputeId(profile, department);
    }

    @Override
    public List<CitationDispute> findTop10ByClientProfileOrderByDisputeIdDesc(ClientProfile profile) {
        return bizRepository.findTop10ByClientProfileOrderByDisputeIdDesc(profile);
    }

    @Override
    public Page<CitationDispute> findByOrderByDisputeIdDesc(Pageable pageable) {
        return bizRepository.findByOrderByDisputeIdDesc(pageable);
    }

    @Override
    public List<CitationDispute> findByOrderByDisputeIdDesc() {
        return bizRepository.findByOrderByDisputeIdDesc();
    }

    @Override
    public Page<CitationDispute> findByDisputeStatus(Pageable pageable, String status) {
        return bizRepository.findByDisputeStatus(pageable, status);
    }

    @Override
    public List<CitationDispute> findByDisputeStatusOrderByDisputeIdDesc(String status) {
        return bizRepository.findByDisputeStatusOrderByDisputeIdDesc(status);
    }

}
