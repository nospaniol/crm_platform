package com.crm.service.impl;

import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.OrderShippingFile;
import com.crm.repository.OrderShippingFileRepository;
import com.crm.service.OrderShippingFileService;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderShippingFileServiceImpl implements OrderShippingFileService {

    @Autowired
    private OrderShippingFileRepository bizRepository;

    @Override
    public OrderShippingFile save(OrderShippingFile entity) {
        return bizRepository.save(entity);
    }

    @Override
    public OrderShippingFile update(OrderShippingFile entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(OrderShippingFile entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public OrderShippingFile find(Long id) {
        Optional<OrderShippingFile> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<OrderShippingFile> findAll() {
        return (List<OrderShippingFile>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<OrderShippingFile> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public List<OrderShippingFile> findByClientProfileAndDepartment(ClientProfile profile, Department department) {
        return bizRepository.findByClientProfileAndDepartment(profile, department);
    }

    @Override
    public List<OrderShippingFile> findByClientProfile(ClientProfile profile) {
        return bizRepository.findByClientProfile(profile);
    }

    @Override
    public List<OrderShippingFile> findTop10ByClientProfileAndDepartment(ClientProfile profile, Department department) {
        return bizRepository.findTop10ByClientProfileAndDepartment(profile, department);
    }

    @Override
    public List<OrderShippingFile> findTop10ByClientProfile(ClientProfile profile) {
        return bizRepository.findTop10ByClientProfile(profile);
    }

    @Override
    public List<OrderShippingFile> findBottom10ByClientProfileAndDepartment(ClientProfile profile, Department department) {
        return bizRepository.findBottom10ByClientProfileAndDepartment(profile, department);
    }

    @Override
    public List<OrderShippingFile> findBottom10ByClientProfile(ClientProfile profile) {
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
    public List<OrderShippingFile> findByOrderDate(Date transacationDate) {
        return bizRepository.findByOrderDate(transacationDate);
    }

    @Override
    public OrderShippingFile findByOrderFileId(Long id) {
        return bizRepository.findByOrderFileId(id);
    }

    @Override
    public List<OrderShippingFile> findTop10ByOrderByOrderFileIdDesc() {
        return bizRepository.findTop10ByOrderByOrderFileIdDesc();
    }

    @Override
    public List<OrderShippingFile> findBottom10ByOrderByOrderFileIdDesc() {
        return bizRepository.findBottom10ByOrderByOrderFileIdDesc();
    }

    @Override
    public Page<OrderShippingFile> findAllByOrderByOrderFileIdDesc(Pageable pageable) {
        return bizRepository.findAllByOrderByOrderFileIdDesc(pageable);
    }

}
