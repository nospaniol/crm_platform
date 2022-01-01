package com.crm.service.impl;

import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.VehicleFile;
import com.crm.repository.VehicleFileRepository;
import com.crm.service.VehicleFileService;
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
public class VehicleFileServiceImpl implements VehicleFileService {

    @Autowired
    private VehicleFileRepository bizRepository;

    @Override
    public VehicleFile save(VehicleFile entity) {
        return bizRepository.save(entity);
    }

    @Override
    public VehicleFile update(VehicleFile entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(VehicleFile entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public VehicleFile find(Long id) {
        Optional<VehicleFile> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<VehicleFile> findAll() {
        return (List<VehicleFile>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<VehicleFile> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public VehicleFile findByVehicleFileId(Long id) {
        return bizRepository.findByVehicleFileId(id);
    }

    @Override
    public List<VehicleFile> findTop10ByOrderByVehicleFileIdDesc() {
        return bizRepository.findTop10ByOrderByVehicleFileIdDesc();
    }

    @Override
    public List<VehicleFile> findByClientProfileAndDepartment(ClientProfile profile, Department department) {
        return bizRepository.findByClientProfileAndDepartment(profile, department);
    }

    @Override
    public List<VehicleFile> findByClientProfile(ClientProfile profile) {
        return bizRepository.findByClientProfile(profile);
    }

    @Override
    public List<VehicleFile> findTop10ByClientProfileAndDepartment(ClientProfile profile, Department department) {
        return bizRepository.findTop10ByClientProfileAndDepartment(profile, department);
    }

    @Override
    public List<VehicleFile> findTop10ByClientProfile(ClientProfile profile) {
        return bizRepository.findTop10ByClientProfile(profile);
    }

    @Override
    public List<VehicleFile> findBottom10ByOrderByVehicleFileIdDesc() {
        return bizRepository.findBottom10ByOrderByVehicleFileIdDesc();
    }

    @Override
    public List<VehicleFile> findBottom10ByClientProfileAndDepartment(ClientProfile profile, Department department) {
        return bizRepository.findBottom10ByClientProfileAndDepartment(profile, department);
    }

    @Override
    public List<VehicleFile> findBottom10ByClientProfile(ClientProfile profile) {
        return bizRepository.findBottom10ByClientProfile(profile);
    }

    @Override
    public List<VehicleFile> getSortedPaginatedData(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<VehicleFile> pagedResult = bizRepository.findAll(paging);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<VehicleFile> getPaginatedData(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<VehicleFile> pagedResult = bizRepository.findAll(paging);
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
    public long countByVehicleDate(Date vehicleDate) {
        return bizRepository.countByVehicleDate(vehicleDate);
    }

    @Override
    public List<VehicleFile> findByVehicleDate(Date transacationDate) {
        return bizRepository.findByVehicleDate(transacationDate);
    }

    @Override
    public Page<VehicleFile> findAllByOrderByVehicleFileIdDesc(Pageable pageable) {
        return bizRepository.findAllByOrderByVehicleFileIdDesc(pageable);
    }

}
