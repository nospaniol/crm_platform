package com.crm.service.impl;

import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.Transaction;
import com.crm.model.TransactionDispute;
import com.crm.repository.TransactionDisputeRepository;
import com.crm.service.TransactionDisputeService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransactionDisputeServiceImpl implements TransactionDisputeService {

    @Autowired
    private TransactionDisputeRepository bizRepository;

    @Override
    public TransactionDispute save(TransactionDispute entity) {
        return bizRepository.save(entity);
    }

    @Override
    public TransactionDispute update(TransactionDispute entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(TransactionDispute entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public TransactionDispute find(Long id) {
        Optional<TransactionDispute> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<TransactionDispute> findAll() {
        return (List<TransactionDispute>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<TransactionDispute> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public TransactionDispute findByDisputeId(Long countryId) {
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
    public TransactionDispute findByTransaction(Transaction transaction) {
        return bizRepository.findByTransaction(transaction);
    }

    @Override
    public Page<TransactionDispute> findByClientProfile(Pageable pageable, ClientProfile profile) {
        return bizRepository.findByClientProfile(pageable, profile);
    }

    @Override
    public Page<TransactionDispute> findByClientProfileAndDisputeStatus(Pageable pageable, ClientProfile profile, String status) {
        return bizRepository.findByClientProfileAndDisputeStatus(pageable, profile, status);
    }

    @Override
    public Page<TransactionDispute> findByDepartment(Pageable pageable, Department department) {
        return bizRepository.findByDepartment(pageable, department);
    }

    @Override
    public Page<TransactionDispute> findByDepartmentAndDisputeStatus(Pageable pageable, Department department, String status) {
        return bizRepository.findByDepartmentAndDisputeStatus(pageable, department, status);
    }

    @Override
    public List<TransactionDispute> findTop10ByOrderByDisputeIdDesc() {
        return bizRepository.findTop10ByOrderByDisputeIdDesc();
    }

    @Override
    public List<TransactionDispute> findByDepartmentOrderByDisputeIdDesc(Department department) {
        return bizRepository.findByDepartmentOrderByDisputeIdDesc(department);
    }

    @Override
    public List<TransactionDispute> findByClientProfileAndDisputeStatusOrderByDisputeIdDesc(ClientProfile profile, String status) {
        return bizRepository.findByClientProfileAndDisputeStatusOrderByDisputeIdDesc(profile, status);
    }

    @Override
    public List<TransactionDispute> findByDepartmentAndDisputeStatusOrderByDisputeIdDesc(Department department, String status) {
        return bizRepository.findByDepartmentAndDisputeStatusOrderByDisputeIdDesc(department, status);
    }

    @Override
    public List<TransactionDispute> findByClientProfileOrderByDisputeIdDesc(ClientProfile profile) {
        return bizRepository.findByClientProfileOrderByDisputeIdDesc(profile);
    }

    @Override
    public List<TransactionDispute> findTop10ByClientProfileAndDepartmentOrderByDisputeId(ClientProfile profile, Department department) {
        return bizRepository.findTop10ByClientProfileAndDepartmentOrderByDisputeId(profile, department);
    }

    @Override
    public List<TransactionDispute> findTop10ByClientProfileOrderByDisputeIdDesc(ClientProfile profile) {
        return bizRepository.findTop10ByClientProfileOrderByDisputeIdDesc(profile);
    }

    @Override
    public Page<TransactionDispute> findByOrderByDisputeIdDesc(Pageable pageable) {
        return bizRepository.findByOrderByDisputeIdDesc(pageable);
    }

    @Override
    public List<TransactionDispute> findByOrderByDisputeIdDesc() {
        return bizRepository.findByOrderByDisputeIdDesc();
    }

    @Override
    public Page<TransactionDispute> findByDisputeStatus(Pageable pageable, String status) {
        return bizRepository.findByDisputeStatus(pageable, status);
    }

    @Override
    public List<TransactionDispute> findByDisputeStatusOrderByDisputeIdDesc(String status) {
        return bizRepository.findByDisputeStatusOrderByDisputeIdDesc(status);
    }

}
