package com.crm.service.impl;

import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.OrderAssetFile;
import com.crm.repository.OrderAssetFileRepository;
import com.crm.service.OrderAssetFileService;
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
public class OrderAssetFileServiceImpl implements OrderAssetFileService {

    @Autowired
    private OrderAssetFileRepository bizRepository;

    @Override
    public OrderAssetFile save(OrderAssetFile entity) {
        return bizRepository.save(entity);
    }

    @Override
    public OrderAssetFile update(OrderAssetFile entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(OrderAssetFile entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public OrderAssetFile find(Long id) {
        Optional<OrderAssetFile> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<OrderAssetFile> findAll() {
        return (List<OrderAssetFile>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<OrderAssetFile> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public List<OrderAssetFile> findByClientProfileAndDepartment(ClientProfile profile, Department department) {
        return bizRepository.findByClientProfileAndDepartment(profile, department);
    }

    @Override
    public List<OrderAssetFile> findByClientProfile(ClientProfile profile) {
        return bizRepository.findByClientProfile(profile);
    }

    @Override
    public List<OrderAssetFile> findTop10ByClientProfileAndDepartment(ClientProfile profile, Department department) {
        return bizRepository.findTop10ByClientProfileAndDepartment(profile, department);
    }

    @Override
    public List<OrderAssetFile> findTop10ByClientProfile(ClientProfile profile) {
        return bizRepository.findTop10ByClientProfile(profile);
    }

    @Override
    public List<OrderAssetFile> findBottom10ByClientProfileAndDepartment(ClientProfile profile, Department department) {
        return bizRepository.findBottom10ByClientProfileAndDepartment(profile, department);
    }

    @Override
    public List<OrderAssetFile> findBottom10ByClientProfile(ClientProfile profile) {
        return bizRepository.findBottom10ByClientProfile(profile);
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
    public long countByOrderDate(Date vehicleDate) {
        return bizRepository.countByOrderDate(vehicleDate);
    }

    @Override
    public List<OrderAssetFile> findByOrderDate(Date transacationDate) {
        return bizRepository.findByOrderDate(transacationDate);
    }

    @Override
    public OrderAssetFile findByOrderFileId(Long id) {
        return bizRepository.findByOrderFileId(id);
    }

    @Override
    public List<OrderAssetFile> findTop10ByOrderByOrderFileIdDesc() {
        return bizRepository.findTop10ByOrderByOrderFileIdDesc();
    }

    @Override
    public List<OrderAssetFile> findBottom10ByOrderByOrderFileIdDesc() {
        return bizRepository.findBottom10ByOrderByOrderFileIdDesc();
    }

    @Override
    public Page<OrderAssetFile> findAllByOrderByOrderFileIdDesc(Pageable pageable) {
        return bizRepository.findAllByOrderByOrderFileIdDesc(pageable);
    }

}
