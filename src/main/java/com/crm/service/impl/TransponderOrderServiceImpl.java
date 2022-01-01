package com.crm.service.impl;

import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.TransponderOrder;
import com.crm.repository.TransponderOrderRepository;
import com.crm.service.TransponderOrderService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransponderOrderServiceImpl implements TransponderOrderService {

    @Autowired
    private TransponderOrderRepository bizRepository;

    @Override
    public TransponderOrder save(TransponderOrder entity) {
        return bizRepository.save(entity);
    }

    @Override
    public TransponderOrder update(TransponderOrder entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(TransponderOrder entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public TransponderOrder find(Long id) {
        Optional<TransponderOrder> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override

    public List<TransponderOrder> findAll() {
        return (List<TransponderOrder>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<TransponderOrder> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public TransponderOrder findByOrderId(Long id) {
        return bizRepository.findByOrderId(id);
    }

    @Override
    public List<TransponderOrder> findTop10ByOrderByOrderIdDesc() {
        return bizRepository.findTop10ByOrderByOrderIdDesc();
    }

    @Override
    public List<TransponderOrder> findByClientProfileAndDepartmentOrderByOrderIdDesc(ClientProfile profile, Department department) {
        return bizRepository.findByClientProfileAndDepartmentOrderByOrderIdDesc(profile, department);
    }

    @Override
    public List<TransponderOrder> findByClientProfileOrderByOrderIdDesc(ClientProfile profile) {
        return bizRepository.findByClientProfileOrderByOrderIdDesc(profile);
    }

    @Override
    public long countByClientProfile(ClientProfile profile) {
        return bizRepository.countByClientProfile(profile);
    }

    @Override

    public List<TransponderOrder> findTop10ByClientProfileAndDepartmentOrderByOrderIdDesc(ClientProfile profile, Department department) {
        return bizRepository.findTop10ByClientProfileAndDepartmentOrderByOrderIdDesc(profile, department);
    }

    @Override

    public List<TransponderOrder> findTop10ByClientProfileOrderByOrderIdDesc(ClientProfile profile) {
        return bizRepository.findTop10ByClientProfileOrderByOrderIdDesc(profile);
    }

    @Override
    public long countByDepartment(Department department) {
        return bizRepository.countByDepartment(department);
    }

    @Override
    public long countByClientProfileAndOrderStatus(ClientProfile profile, String status) {
        return bizRepository.countByClientProfileAndOrderStatus(profile, status);
    }

    @Override
    public long countByDepartmentAndOrderStatus(Department department, String status) {
        return bizRepository.countByDepartmentAndOrderStatus(department, status);
    }

    @Override

    public Page<TransponderOrder> findByClientProfile(Pageable pageable, ClientProfile profile) {
        return bizRepository.findByClientProfile(pageable, profile);
    }

    @Override

    public Page<TransponderOrder> findByDepartment(Pageable pageable, Department department) {
        return bizRepository.findByDepartment(pageable, department);
    }

    @Override

    public Page<TransponderOrder> findAllByOrderIdDesc(Pageable pageable) {
        return bizRepository.findAllByOrderByOrderIdDesc(pageable);
    }

    @Override
    public List<TransponderOrder> findAllByOrderIdDesc() {
        return bizRepository.findAllByOrderByOrderIdDesc();
    }

    @Override
    public List<TransponderOrder> findByLicensePlateOrderByOrderIdDesc(String plate) {
        return bizRepository.findByLicensePlateOrderByOrderIdDesc(plate);
    }

    @Override
    public List<TransponderOrder> findByDepartmentOrderByOrderIdDesc(Department department) {
        return bizRepository.findByDepartmentOrderByOrderIdDesc(department);
    }

    @Override
    public TransponderOrder findByClientProfileAndOrderId(ClientProfile profile, Long id) {
        return bizRepository.findByClientProfileAndOrderId(profile, id);
    }

    @Override
    public TransponderOrder findByDepartmentAndOrderId(Department profile, Long id) {
        return bizRepository.findByDepartmentAndOrderId(profile, id);
    }

}
