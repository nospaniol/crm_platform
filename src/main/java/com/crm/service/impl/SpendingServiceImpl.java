package com.crm.service.impl;

import com.crm.model.Spending;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.Vehicle;
import com.crm.repository.SpendingRepository;
import com.crm.service.SpendingService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SpendingServiceImpl implements SpendingService {

    @Autowired
    private SpendingRepository bizRepository;

    @Override
    public Spending save(Spending entity) {
        return bizRepository.save(entity);
    }

    @Override
    public Spending update(Spending entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(Spending entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public Spending find(Long id) {
        Optional<Spending> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<Spending> findAll() {
        return (List<Spending>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<Spending> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public Spending findBySpendingsId(Long id) {
        return bizRepository.findBySpendingsId(id);
    }

    @Override
    public List<Spending> findByClientProfile(ClientProfile clientProfile) {
        return bizRepository.findByClientProfile(clientProfile);
    }

    @Override
    public List<Spending> findByDepartment(Department department) {
        return bizRepository.findByDepartment(department);
    }

    @Override
    public List<Spending> findByVehicle(Vehicle vehicle) {
        return bizRepository.findByVehicle(vehicle);
    }

    @Override
    public List<Spending> findTop10ByOrderBySpendingsIdDesc() {
        return bizRepository.findTop10ByOrderBySpendingsIdDesc();
    }

    @Override
    public List<Spending> findTop10ByClientProfileOrderBySpendingsIdDesc(ClientProfile clientProfile) {
        return bizRepository.findTop10ByClientProfileOrderBySpendingsIdDesc(clientProfile);
    }

    @Override
    public List<Spending> findByClientProfileOrderBySpendingsIdDesc(ClientProfile clientProfile) {
        return bizRepository.findByClientProfileOrderBySpendingsIdDesc(clientProfile);
    }

    @Override
    public List<Spending> findByDepartmentOrderBySpendingsIdDesc(Department department) {
        return bizRepository.findByDepartmentOrderBySpendingsIdDesc(department);
    }

    @Override
    public List<Spending> findTop10ByDepartmentOrderBySpendingsIdDesc(Department department) {
        return bizRepository.findTop10ByDepartmentOrderBySpendingsIdDesc(department);
    }

    @Override
    public List<Spending> findByClientProfileAndSpendingMonthAndSpendingYearOrderBySpendingsIdDesc(ClientProfile profile, String spendingMonth, int spendingYear) {
        return bizRepository.findByClientProfileAndSpendingMonthAndSpendingYearOrderBySpendingsIdDesc(profile, spendingMonth, spendingYear);
    }

    @Override
    public List<Spending> findByDepartmentAndSpendingMonthAndSpendingYearOrderByAmountDesc(Department profile, String spendingMonth, int spendingYear) {
        return bizRepository.findByDepartmentAndSpendingMonthAndSpendingYearOrderBySpendingsIdDesc(profile, spendingMonth, spendingYear);
    }

    @Override
    public List<Spending> findBySpendingMonthAndSpendingYearOrderBySpendingsIdDesc(String spendingMonth, int spendingYear) {
        return bizRepository.findBySpendingMonthAndSpendingYearOrderBySpendingsIdDesc(spendingMonth, spendingYear);
    }

    @Override
    public Spending findByVehicleAndSpendingMonthAndSpendingYear(Vehicle vehicle, String spendingMonth, int spendingYear) {
        return bizRepository.findByVehicleAndSpendingMonthAndSpendingYear(vehicle, spendingMonth, spendingYear);
    }

    @Override
    public List<Spending> findByDepartmentAndSpendingMonthAndSpendingYearOrderBySpendingsIdDesc(Department profile, String spendingMonth, int spendingYear) {
        return bizRepository.findByDepartmentAndSpendingMonthAndSpendingYearOrderBySpendingsIdDesc(profile, spendingMonth, spendingYear);
    }

    @Override
    public List<Spending> findByClientProfileAndSpendingMonthAndSpendingYear(Sort sort, ClientProfile profile, String spendingMonth, int spendingYear) {
        return bizRepository.findByClientProfileAndSpendingMonthAndSpendingYear(sort, profile, spendingMonth, spendingYear);
    }

}
