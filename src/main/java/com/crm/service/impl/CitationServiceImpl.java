package com.crm.service.impl;

import com.crm.model.Citation;
import com.crm.model.CitationType;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.Vehicle;
import com.crm.repository.CitationRepository;
import com.crm.service.CitationService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CitationServiceImpl implements CitationService {

    @Autowired
    private CitationRepository bizRepository;

    @Override
    public Citation save(Citation entity) {
        return bizRepository.save(entity);
    }

    @Override
    public Citation update(Citation entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(Citation entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public Citation find(Long id) {
        Optional<Citation> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<Citation> findAll() {
        return (List<Citation>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<Citation> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public Citation findByCitationId(Long id) {
        return bizRepository.findByCitationId(id);
    }

    @Override
    public long countAll() {
        return bizRepository.count();
    }

    @Override
    public Page<Citation> findAll(Pageable pageable) {
        return bizRepository.findAll(pageable);
    }

    @Override
    public List<Citation> findByClientProfile(ClientProfile clientProfile) {
        return bizRepository.findByClientProfile(clientProfile);
    }

    @Override
    public List<Citation> findByDepartment(Department department) {
        return bizRepository.findByDepartment(department);
    }

    @Override
    public List<Citation> findByClientProfileAndCitationStatus(ClientProfile clientProfile, String citationStatus) {
        return bizRepository.findByClientProfileAndCitationStatus(clientProfile, citationStatus);
    }

    @Override
    public List<Citation> findByDepartmentAndCitationStatus(Department department, String citationStatus) {
        return bizRepository.findByDepartmentAndCitationStatus(department, citationStatus);
    }

    @Override
    public List<Citation> findByVehicle(Vehicle vehicle) {
        return bizRepository.findByVehicle(vehicle);
    }

    @Override
    public List<Citation> findByVehicleAndCitationStatus(Vehicle vehicle, String status) {
        return bizRepository.findByVehicleAndCitationStatus(vehicle, status);
    }

    @Override
    public List<Citation> findTop10ByOrderByCitationIdDesc() {
        return bizRepository.findTop10ByOrderByCitationIdDesc();
    }

    @Override
    public List<Citation> findTop10ByClientProfileOrderByCitationIdDesc(ClientProfile clientProfile) {
        return bizRepository.findTop10ByClientProfileOrderByCitationIdDesc(clientProfile);
    }

    @Override
    public List<Citation> findByClientProfileOrderByCitationIdDesc(ClientProfile clientProfile) {
        return bizRepository.findByClientProfileOrderByCitationIdDesc(clientProfile);
    }

    @Override
    public List<Citation> findByDepartmentOrderByCitationIdDesc(Department department) {
        return bizRepository.findByDepartmentOrderByCitationIdDesc(department);
    }

    @Override
    public List<Citation> findTop10ByDepartmentOrderByCitationIdDesc(Department department) {
        return bizRepository.findTop10ByDepartmentOrderByCitationIdDesc(department);
    }

    @Override
    public List<Citation> findByClientProfileAndCitationMonthAndCitationYearOrderByCitationIdDesc(ClientProfile profile, String citationMonth, int citationYear) {
        return bizRepository.findByClientProfileAndCitationMonthAndCitationYearOrderByCitationIdDesc(profile, citationMonth, citationYear);
    }

    @Override
    public List<Citation> findByDepartmentAndCitationMonthAndCitationYearOrderByCitationIdDesc(Department profile, String citationMonth, int citationYear) {
        return bizRepository.findByDepartmentAndCitationMonthAndCitationYearOrderByCitationIdDesc(profile, citationMonth, citationYear);
    }

    @Override
    public List<Citation> findByClientProfileAndCitationMonthAndCitationYearAndCitationTypeOrderByCitationIdDesc(ClientProfile profile, String citationMonth, int citationYear, CitationType type) {
        return bizRepository.findByClientProfileAndCitationMonthAndCitationYearAndCitationTypeOrderByCitationIdDesc(profile, citationMonth, citationYear, type);
    }

    @Override
    public List<Citation> findByDepartmentAndCitationMonthAndCitationYearAndCitationTypeOrderByCitationIdDesc(Department profile, String citationMonth, int citationYear, CitationType type) {
        return bizRepository.findByDepartmentAndCitationMonthAndCitationYearAndCitationTypeOrderByCitationIdDesc(profile, citationMonth, citationYear, type);
    }

    @Override
    public long countByClientProfileAndCitationMonthAndCitationYearAndCitationType(ClientProfile profile, String citationMonth, int citationYear, CitationType type) {
        return bizRepository.countByClientProfileAndCitationMonthAndCitationYearAndCitationType(profile, citationMonth, citationYear, type);
    }

    @Override
    public long countByDepartmentAndCitationMonthAndCitationYearAndCitationType(Department department, String citationMonth, int citationYear, CitationType type) {
        return bizRepository.countByDepartmentAndCitationMonthAndCitationYearAndCitationType(department, citationMonth, citationYear, type);
    }

}
