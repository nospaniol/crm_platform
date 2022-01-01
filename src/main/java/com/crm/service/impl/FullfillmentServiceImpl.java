package com.crm.service.impl;

import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.Fullfillment;
import com.crm.repository.FullfillmentRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.crm.service.FullfillmentService;

@Service
public class FullfillmentServiceImpl implements FullfillmentService {

    @Autowired
    private FullfillmentRepository bizRepository;

    @Override
    public Fullfillment save(Fullfillment entity) {
        return bizRepository.save(entity);
    }

    @Override
    public Fullfillment update(Fullfillment entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(Fullfillment entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public Fullfillment find(Long id) {
        Optional<Fullfillment> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override

    public List<Fullfillment> findAll() {
        return (List<Fullfillment>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<Fullfillment> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public Fullfillment findByOrderId(Long id) {
        return bizRepository.findByOrderId(id);
    }

    @Override
    public List<Fullfillment> findTop10ByOrderByOrderIdDesc() {
        return bizRepository.findTop10ByOrderByOrderIdDesc();
    }

    @Override
    public List<Fullfillment> findByClientProfileAndDepartmentOrderByOrderIdDesc(ClientProfile profile, Department department) {
        return bizRepository.findByClientProfileAndDepartmentOrderByOrderIdDesc(profile, department);
    }

    @Override
    public List<Fullfillment> findByClientProfileOrderByOrderIdDesc(ClientProfile profile) {
        return bizRepository.findByClientProfileOrderByOrderIdDesc(profile);
    }

    @Override
    public long countByClientProfile(ClientProfile profile) {
        return bizRepository.countByClientProfile(profile);
    }

    @Override

    public List<Fullfillment> findTop10ByClientProfileAndDepartmentOrderByOrderIdDesc(ClientProfile profile, Department department) {
        return bizRepository.findTop10ByClientProfileAndDepartmentOrderByOrderIdDesc(profile, department);
    }

    @Override

    public List<Fullfillment> findTop10ByClientProfileOrderByOrderIdDesc(ClientProfile profile) {
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

    public Page<Fullfillment> findByClientProfile(Pageable pageable, ClientProfile profile) {
        return bizRepository.findByClientProfile(pageable, profile);
    }

    @Override

    public Page<Fullfillment> findByDepartment(Pageable pageable, Department department) {
        return bizRepository.findByDepartment(pageable, department);
    }

    @Override

    public Page<Fullfillment> findAllByOrderIdDesc(Pageable pageable) {
        return bizRepository.findAllByOrderByOrderIdDesc(pageable);
    }

    @Override
    public List<Fullfillment> findAllByOrderIdDesc() {
        return bizRepository.findAllByOrderByOrderIdDesc();
    }

    @Override
    public List<Fullfillment> findByLicensePlateOrderByOrderIdDesc(String plate) {
        return bizRepository.findByLicensePlateOrderByOrderIdDesc(plate);
    }

    @Override
    public List<Fullfillment> findByDepartmentOrderByOrderIdDesc(Department department) {
        return bizRepository.findByDepartmentOrderByOrderIdDesc(department);
    }

    @Override
    public Fullfillment findByClientProfileAndOrderId(ClientProfile profile, Long id) {
        return bizRepository.findByClientProfileAndOrderId(profile, id);
    }

    @Override
    public Fullfillment findByDepartmentAndOrderId(Department profile, Long id) {
        return bizRepository.findByDepartmentAndOrderId(profile, id);
    }

}
