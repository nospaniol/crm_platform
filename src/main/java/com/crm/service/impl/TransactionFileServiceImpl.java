package com.crm.service.impl;

import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.TransactionFile;
import com.crm.repository.TransactionFileRepository;
import com.crm.service.TransactionFileService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TransactionFileServiceImpl implements TransactionFileService {

    @Autowired
    private TransactionFileRepository bizRepository;

    @Override
    public TransactionFile save(TransactionFile entity) {
        return bizRepository.save(entity);
    }

    @Override
    public TransactionFile update(TransactionFile entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(TransactionFile entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public TransactionFile find(Long id) {
        Optional<TransactionFile> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<TransactionFile> findAll() {
        return (List<TransactionFile>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<TransactionFile> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public TransactionFile findByTransactionFileId(Long id) {
        return bizRepository.findByTransactionFileId(id);
    }

    @Override
    public List<TransactionFile> findTop10ByOrderByTransactionFileIdDesc() {
        return bizRepository.findTop10ByOrderByTransactionFileIdDesc();
    }

    @Override
    public List<TransactionFile> findByClientProfileAndDepartment(ClientProfile profile, Department department) {
        return bizRepository.findByClientProfileAndDepartment(profile, department);
    }

    @Override
    public List<TransactionFile> findByClientProfile(ClientProfile profile) {
        return bizRepository.findByClientProfile(profile);
    }

    @Override
    public List<TransactionFile> findTop10ByClientProfileAndDepartment(ClientProfile profile, Department department) {
        return bizRepository.findTop10ByClientProfileAndDepartment(profile, department);
    }

    @Override
    public List<TransactionFile> findTop10ByClientProfile(ClientProfile profile) {
        return bizRepository.findTop10ByClientProfile(profile);
    }

    @Override
    public List<TransactionFile> findBottom10ByOrderByTransactionFileIdDesc() {
        return bizRepository.findBottom10ByOrderByTransactionFileIdDesc();
    }

    @Override
    public List<TransactionFile> findBottom10ByClientProfileAndDepartment(ClientProfile profile, Department department) {
        return bizRepository.findBottom10ByClientProfileAndDepartment(profile, department);
    }

    @Override
    public List<TransactionFile> findBottom10ByClientProfile(ClientProfile profile) {
        return bizRepository.findBottom10ByClientProfile(profile);
    }

    @Override
    public List<TransactionFile> getSortedPaginatedData(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<TransactionFile> pagedResult = bizRepository.findAll(paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<TransactionFile> getPaginatedData(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<TransactionFile> pagedResult = bizRepository.findAll(paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public long countAll() {
        return bizRepository.count();
    }

    @Override
    public long countByClientProfile(ClientProfile profile) {
        return bizRepository.countByClientProfile(profile);
    }

    @Override
    public long countByClientProfileAndDepartment(ClientProfile profile, Department department) {
        return bizRepository.countByClientProfileAndDepartment(profile, department);
    }

    @Override
    public long countByTransactionDate(Date transactionDate) {
        return bizRepository.countByTransactionDate(transactionDate);
    }

    @Override
    public List<TransactionFile> findByTransactionDate(Date transacationDate) {
        return bizRepository.findByTransactionDate(transacationDate);
    }

    @Override
    public Page<TransactionFile> findAllByOrderByTransactionFileIdDesc(Pageable pageable) {
        return bizRepository.findAllByOrderByTransactionFileIdDesc(pageable);
    }

}
