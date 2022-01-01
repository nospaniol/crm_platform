package com.crm.service.impl;

import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.Vehicle;
import com.crm.model.VehicleFile;
import com.crm.repository.VehicleRepository;
import com.crm.service.VehicleService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository bizRepository;

    @Override
    public Vehicle save(Vehicle entity) {
        return bizRepository.save(entity);
    }

    @Override
    public Vehicle update(Vehicle entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(Vehicle entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public Vehicle find(Long id) {
        Optional<Vehicle> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    //@Cacheable("vehicles")
    public List<Vehicle> findAll() {
        return (List<Vehicle>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<Vehicle> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public Vehicle findByVehicleId(Long id) {
        return bizRepository.findByVehicleId(id);
    }

    @Override
    public Vehicle findByLicensePlate(String plate) {
        return bizRepository.findByLicensePlate(plate);
    }

    @Override
    public List<Vehicle> findByTollTagId(String tolltag) {
        return bizRepository.findByTollTagId(tolltag);
    }

    @Override
    //@Cacheable("vehicles")
    public List<Vehicle> findTop10ByOrderByVehicleIdDesc() {
        return bizRepository.findTop10ByOrderByVehicleIdDesc();
    }

    @Override
    //@Cacheable("vehicles")
    public List<Vehicle> findByClientProfileAndDepartmentOrderByVehicleIdDesc(ClientProfile profile, Department department) {
        return bizRepository.findByClientProfileAndDepartmentOrderByVehicleIdDesc(profile, department);
    }

    @Override
    //@Cacheable("vehicles")
    public List<Vehicle> findByClientProfileOrderByVehicleIdDesc(ClientProfile profile) {
        return bizRepository.findByClientProfileOrderByVehicleIdDesc(profile);
    }

    @Override
    //@Cacheable("vehicles")
    public Page<Vehicle> getSortedPaginatedData(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Vehicle> pagedResult = bizRepository.findAll(paging);
        return pagedResult;
    }

    @Override
    //@Cacheable("vehicles")
    public Page<Vehicle> getPaginatedData(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<Vehicle> pagedResult = bizRepository.findAll(paging);
        return pagedResult;
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
    //@Cacheable("vehicles")
    public List<Vehicle> findTop10ByClientProfileAndDepartmentOrderByVehicleIdDesc(ClientProfile profile, Department department) {
        return bizRepository.findTop10ByClientProfileAndDepartmentOrderByVehicleIdDesc(profile, department);
    }

    @Override
    //@Cacheable("vehicles")
    public List<Vehicle> findTop10ByClientProfileOrderByVehicleIdDesc(ClientProfile profile) {
        return bizRepository.findTop10ByClientProfileOrderByVehicleIdDesc(profile);
    }

    @Override
    //@Cacheable("vehicles")
    public Page<Vehicle> getPageable(Pageable pageable) {
        return bizRepository.findAll(pageable);
    }

    @Override
    public long countByDepartment(Department department) {
        return bizRepository.countByDepartment(department);
    }

    @Override
    public long countByClientProfileAndVehicleStatus(ClientProfile profile, String status) {
        return bizRepository.countByClientProfileAndVehicleStatus(profile, status);
    }

    @Override
    public long countByDepartmentAndVehicleStatus(Department department, String status) {
        return bizRepository.countByDepartmentAndVehicleStatus(department, status);
    }

    @Override
    //@Cacheable("vehicles")
    public Page<Vehicle> findByClientProfile(Pageable pageable, ClientProfile profile) {
        return bizRepository.findByClientProfile(pageable, profile);
    }

    @Override
    //@Cacheable("vehicles")
    public Page<Vehicle> findByDepartment(Pageable pageable, Department department) {
        return bizRepository.findByDepartment(pageable, department);
    }

    @Override
    //@Cacheable("vehicles")
    public Page<Vehicle> findAllByOrderByVehicleIdDesc(Pageable pageable) {
        return bizRepository.findAllByOrderByVehicleIdDesc(pageable);
    }

    @Override
    //@Cacheable("vehicles")
    public List<Vehicle> findAllByOrderByVehicleIdDesc() {
        return bizRepository.findAllByOrderByVehicleIdDesc();
    }

    @Override
    //@Cacheable("vehicles")
    public Page<Vehicle> findAll(Pageable pageable) {
        return bizRepository.findAll(pageable);
    }

    @Override
    //@Cacheable("vehicles")
    public List<Vehicle> findByLicensePlateOrderByVehicleIdDesc(String plate) {
        return bizRepository.findByLicensePlateOrderByVehicleIdDesc(plate);
    }

    @Override
    //@Cacheable("vehicles")
    public List<Vehicle> findByVehicleFile(VehicleFile id) {
        return bizRepository.findByVehicleFile(id);
    }

    @Override
    public Vehicle findByVin(String vin) {
        return bizRepository.findByVin(vin);
    }

    @Override
    public void deleteInBatch(Iterable<Vehicle> entities) {
        bizRepository.deleteAll(entities);
    }

    @Override
    public Page<Vehicle> findByClientProfileAndTollTagIdNot(Pageable pageable, ClientProfile profile, String tolltag) {
        return bizRepository.findByClientProfileAndTollTagIdNot(pageable, profile, tolltag);
    }

    @Override
    public Page<Vehicle> findByDepartmentAndTollTagIdNot(Pageable pageable, Department department, String tolltag) {
        return bizRepository.findByDepartmentAndTollTagIdNot(pageable, department, tolltag);
    }

    @Override
    public Page<Vehicle> findByClientProfileAndVinNot(Pageable pageable, ClientProfile profile, String tolltag) {
        return bizRepository.findByClientProfileAndVinNot(pageable, profile, tolltag);
    }

    @Override
    public Page<Vehicle> findByDepartmentAndVinNot(Pageable pageable, Department department, String tolltag) {
        return bizRepository.findByDepartmentAndVinNot(pageable, department, tolltag);
    }

    @Override
    public Page<Vehicle> findByClientProfileAndVinNotAndStateNot(Pageable pageable, ClientProfile profile, String tolltag, String ortolltag) {
        return bizRepository.findByClientProfileAndVinNotAndStateNot(pageable, profile, tolltag, ortolltag);
    }

    @Override
    public Page<Vehicle> findByDepartmentAndVinNotAndStateNot(Pageable pageable, Department department, String tolltag, String ortolltag) {
        return bizRepository.findByDepartmentAndVinNotAndStateNot(pageable, department, tolltag, ortolltag);
    }

    @Override
    public long countByClientProfileAndVinNotAndStateNot(ClientProfile profile, String tolltag, String state) {
        return bizRepository.countByClientProfileAndVinNotAndStateNot(profile, tolltag, state);
    }

    @Override
    public long countByDepartmentAndVinNotAndStateNot(Department department, String tolltag, String state) {
        return bizRepository.countByDepartmentAndVinNotAndStateNot(department, tolltag, state);
    }

    @Override
    public List<Vehicle> findByClientProfileAndTollTagIdLike(ClientProfile profile, String tollTagId) {
        return bizRepository.findByClientProfileAndTollTagIdIgnoreCaseLike(profile, tollTagId);
    }

    @Override
    public List<Vehicle> findByClientProfileAndVinLike(ClientProfile profile, String vin) {
        return bizRepository.findByClientProfileAndVinIgnoreCaseLike(profile, vin);
    }

    @Override
    public List<Vehicle> findByClientProfileAndLicensePlateLike(ClientProfile profile, String licensePlate) {
        return bizRepository.findByClientProfileAndLicensePlateIgnoreCaseLike(profile, licensePlate);
    }

    @Override
    public Page<Vehicle> findByClientProfileAndState(Pageable pageable, ClientProfile profile, String state) {
        return bizRepository.findByClientProfileAndState(pageable, profile, state);
    }

    @Override
    public Page<Vehicle> findByClientProfileAndVinNotAndStateNotOrStateNot(Pageable pageable, ClientProfile profile, String tolltag, String state, String state1) {
        return bizRepository.findByClientProfileAndVinNotAndStateNotOrStateNot(pageable, profile, tolltag, state, state1);
    }

    @Override
    public Page<Vehicle> findByDepartmentAndState(Pageable pageable, Department department, String state) {
        return bizRepository.findByDepartmentAndState(pageable, department, state);
    }

    @Override
    public Page<Vehicle> findByDepartmentAndVinNotAndStateNotOrStateNot(Pageable pageable, Department department, String tolltag, String state, String state1) {
        return bizRepository.findByDepartmentAndVinNotAndStateNotOrStateNot(pageable, department, tolltag, state, state1);
    }

}
