package com.crm.service.impl;

import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.Transaction;
import com.crm.model.Vehicle;
import com.crm.repository.TransactionRepository;
import com.crm.service.TransactionService;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository bizRepository;

    @Override
    public Transaction save(Transaction entity) {
        return bizRepository.save(entity);
    }

    @Override
    public Transaction update(Transaction entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(Transaction entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public Transaction find(Long id) {
        Optional<Transaction> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override

    public List<Transaction> findAll() {
        return bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<Transaction> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public Transaction findByTransactionId(Long id) {
        return bizRepository.findByTransactionId(id);
    }

    @Override

    public List<Transaction> findTop10ByOrderByTransactionIdDesc() {
        return bizRepository.findTop10ByOrderByTransactionIdDesc();
    }

    @Override

    public List<Transaction> findByClientProfileAndDepartmentOrderByTransactionIdDesc(ClientProfile profile, Department department) {
        return bizRepository.findByClientProfileAndDepartmentOrderByTransactionIdDesc(profile, department);
    }

    @Override

    public List<Transaction> findByClientProfileOrderByTransactionIdDesc(ClientProfile profile) {
        return bizRepository.findByClientProfileOrderByTransactionIdDesc(profile);
    }

    @Override

    public List<Transaction> findTop10ByClientProfileAndDepartmentOrderByTransactionId(ClientProfile profile, Department department) {
        return bizRepository.findTop10ByClientProfileAndDepartmentOrderByTransactionId(profile, department);
    }

    @Override

    public List<Transaction> findTop10ByClientProfileOrderByTransactionIdDesc(ClientProfile profile) {
        return bizRepository.findTop10ByClientProfileOrderByTransactionIdDesc(profile);
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
    public long countByTransactionDate(Date transactionDate) {
        return bizRepository.countByTransactionDate(transactionDate);
    }

    @Override

    public Page<Transaction> findByVehicleAndTransactionDate(Pageable pageable, Vehicle vehicle, Date transactionDate) {
        return bizRepository.findByVehicleAndTransactionDate(pageable, vehicle, transactionDate);
    }

    @Override

    public Page<Transaction> findByVehicle(Pageable pageable, Vehicle vehicle) {
        return bizRepository.findByVehicle(pageable, vehicle);
    }

    @Override
    public List<Transaction> findByExitDateTimeAndVehicle(String exitDateTime, Vehicle vehicle) {
        return bizRepository.findByExitDateTimeAndVehicle(exitDateTime, vehicle);
    }

    @Override
    public List<Transaction> findByExitDateTimeAndExitLocationAndVehicle(String exitDateTime, String exitLocation, Vehicle vehicle) {
        return bizRepository.findByExitDateTimeAndExitLocationAndVehicle(exitDateTime, exitLocation, vehicle);
    }

    @Override
    public List<Transaction> findByExitDateTimeAndExitLaneAndVehicle(Date exitDateTime, String exitLane, Vehicle vehicle) {
        return bizRepository.findByExitDateTimeAndExitLaneAndVehicle(exitDateTime, exitLane, vehicle);
    }

    @Override
    public List<Transaction> findByExitDateTimeAndExitLocationAndExitLaneAndVehicle(Date exitDateTime, String exitLocation, String exitLane, Vehicle vehicle) {
        return bizRepository.findByExitDateTimeAndExitLocationAndExitLaneAndVehicle(exitDateTime, exitLocation, exitLane, vehicle);
    }

    @Override
    public List<Transaction> findByClientProfileAndExitDateTimeAndVehicle(ClientProfile profile, String exitDateTime, Vehicle vehicle) {
        return bizRepository.findByClientProfileAndExitDateTimeAndVehicle(profile, exitDateTime, vehicle);
    }

    @Override
    public List<Transaction> findByClientProfileAndExitDateTimeAndExitLocationAndVehicle(ClientProfile profile, String exitDateTime, String exitLocation, Vehicle vehicle) {
        return bizRepository.findByClientProfileAndExitDateTimeAndExitLocationAndVehicle(profile, exitDateTime, exitLocation, vehicle);
    }

    @Override
    public List<Transaction> findByClientProfileAndExitDateTimeAndExitLaneAndVehicle(ClientProfile profile, Date exitDateTime, String exitLane, Vehicle vehicle) {
        return bizRepository.findByClientProfileAndExitDateTimeAndExitLaneAndVehicle(profile, exitDateTime, exitLane, vehicle);
    }

    @Override
    public List<Transaction> findByClientProfileAndExitDateTimeAndExitLaneAndVehicleAndAmount(ClientProfile profile, Date exitDateTime, String exitLane, Vehicle vehicle, Double amount) {
        return bizRepository.findByClientProfileAndExitDateTimeAndExitLaneAndVehicleAndAmount(profile, exitDateTime, exitLane, vehicle, amount);
    }

    @Override
    public List<Transaction> findByClientProfileAndExitDateTimeAndExitLocationAndExitLaneAndVehicle(ClientProfile profile, Date exitDateTime, String exitLocation, String exitLane, Vehicle vehicle) {
        return null;//bizRepository.findByClientProfileAndExitDateTimeAndExitLocationAndExitLaneAndVehicle(profile, exitDateTime, exitLocation, exitLane, vehicle);
    }

    @Override

    public List<Transaction> findByTransactionFileId(Long id) {
        return bizRepository.findByTransactionFileId(id);
    }

    @Override

    public Page<Transaction> findByTransactionDate(Pageable pageable, Date transacationDate) {
        return bizRepository.findByTransactionDate(pageable, transacationDate);
    }

    @Override

    public List<Transaction> findByClientProfileAndTransactionDateOrderByTransactionIdDesc(ClientProfile profile, Date transacationDate) {
        return bizRepository.findByClientProfileAndTransactionDateOrderByTransactionIdDesc(profile, transacationDate);
    }

    @Override

    public List<Transaction> findByClientProfileAndDepartmentAndTransactionDate(ClientProfile profile, Department department, Date transacationDate) {
        return bizRepository.findByClientProfileAndDepartmentAndTransactionDate(profile, department, transacationDate);
    }

    @Override

    public Page<Transaction> findByOrderByTransactionIdDesc(Pageable pageable) {
        return bizRepository.findByOrderByTransactionIdDesc(pageable);
    }

    @Override

    public List<Transaction> findByTransactionDate(Date transacationDate) {
        return bizRepository.findByTransactionDate(transacationDate);
    }

    @Override

    public List<Transaction> findByTransactionMonthAndTransactionYear(String transactionMonth, int year) {
        return bizRepository.findByTransactionMonthAndTransactionYear(transactionMonth, year);
    }

    @Override

    public List<Transaction> findByClientProfileAndTransactionMonthAndTransactionYear(ClientProfile profile, String transactionMonth, int year) {
        return bizRepository.findByClientProfileAndTransactionMonthAndTransactionYear(profile, transactionMonth, year);
    }

    @Override

    public List<Transaction> findByClientProfileAndTransactionYear(ClientProfile profile, int year) {
        return bizRepository.findByClientProfileAndTransactionYear(profile, year);
    }

    @Override

    public List<Transaction> findByClientProfileAndTransactionMonth(ClientProfile profile, String transactionMonth) {
        return bizRepository.findByClientProfileAndTransactionMonth(profile, transactionMonth);
    }

    @Override

    public List<Transaction> findByDepartmentAndTransactionMonthAndTransactionYear(Department department, String transactionMonth, int year) {
        return bizRepository.findByDepartmentAndTransactionMonthAndTransactionYear(department, transactionMonth, year);
    }

    @Override

    public List<Transaction> findByDepartmentAndTransactionYear(Department department, int year) {
        return bizRepository.findByDepartmentAndTransactionYear(department, year);
    }

    @Override

    public List<Transaction> findByDepartmentAndTransactionMonth(Department department, String transactionMonth) {
        return bizRepository.findByDepartmentAndTransactionMonth(department, transactionMonth);
    }

    @Override

    public List<Transaction> findByOrderByTransactionIdDesc() {
        return bizRepository.findByOrderByTransactionIdDesc();
    }

    @Override

    public List<Transaction> findByVehicleAndTransactionDate(Vehicle vehicle, Date transactionDate) {
        return bizRepository.findByVehicleAndTransactionDate(vehicle, transactionDate);
    }

    @Override

    public List<Transaction> findByVehicle(Vehicle vehicle) {
        return bizRepository.findByVehicle(vehicle);
    }

    @Override

    public List<Transaction> findByVehicleAndPostedDate(Vehicle vehicle, Date postedDate) {
        return bizRepository.findByVehicleAndPostedDate(vehicle, postedDate);
    }

    @Override

    public List<Transaction> findByClientProfileAndPostedDateOrderByTransactionIdDesc(ClientProfile profile, Date transacationDate) {
        return bizRepository.findByClientProfileAndPostedDateOrderByTransactionIdDesc(profile, transacationDate);
    }

    @Override
    public long countByPostedDate(Date postedDate) {
        return bizRepository.countByPostedDate(postedDate);
    }

    @Override

    public Page<Transaction> findByVehicleAndPostedDate(Pageable pageable, Vehicle vehicle, Date postedDate) {
        return bizRepository.findByVehicleAndPostedDate(pageable, vehicle, postedDate);
    }

    @Override

    public Page<Transaction> findByPostedDate(Pageable pageable, Date transacationDate) {
        return bizRepository.findByPostedDate(pageable, transacationDate);
    }

    @Override

    public List<Transaction> findByPostedDate(Date transacationDate) {
        return bizRepository.findByPostedDate(transacationDate);
    }

    @Override
    public long countByClientProfileAndPostedDate(ClientProfile profile, Date transacationDate) {
        return bizRepository.countByClientProfileAndPostedDate(profile, transacationDate);
    }

    @Override
    public long countByDepartmentAndPostedDate(Department department, Date transacationDate) {
        return bizRepository.countByDepartmentAndPostedDate(department, transacationDate);
    }

    @Override

    public List<Transaction> findByDepartmentAndTransactionDateOrderByTransactionIdDesc(Department department, Date transacationDate) {
        return bizRepository.findByDepartmentAndTransactionDateOrderByTransactionIdDesc(department, transacationDate);
    }

    @Override

    public List<Transaction> findByDepartmentAndPostedDate(Department department, Date transacationDate) {
        return bizRepository.findByDepartmentAndPostedDate(department, transacationDate);
    }

    @Override

    public List<Transaction> findByClientProfileAndTransactionMonthAndTransactionYearAndAgency(ClientProfile profile, String transactionMonth, int year, String agency) {
        return bizRepository.findByClientProfileAndTransactionMonthAndTransactionYearAndAgency(profile, transactionMonth, year, agency);
    }

    @Override

    public List<Transaction> findByDepartmentAndTransactionMonthAndTransactionYearAndAgency(Department department, String transactionMonth, int year, String agency) {
        return bizRepository.findByDepartmentAndTransactionMonthAndTransactionYearAndAgency(department, transactionMonth, year, agency);
    }

    @Override

    public List<Transaction> findByDepartmentAndTransactionMonthAndTransactionYearAndVehicle(Department department, String transactionMonth, int year, Vehicle vehicle) {
        return bizRepository.findByDepartmentAndTransactionMonthAndTransactionYearAndVehicle(department, transactionMonth, year, vehicle);
    }

    @Override

    public List<Transaction> findByClientProfileAndTransactionMonthAndTransactionYearAndVehicle(ClientProfile profile, String transactionMonth, int year, Vehicle vehicle) {
        return bizRepository.findByClientProfileAndTransactionMonthAndTransactionYearAndVehicle(profile, transactionMonth, year, vehicle);
    }

    @Override

    public Page<Transaction> findByClientProfileOrderByTransactionIdDesc(Pageable pageable, ClientProfile profile) {
        return bizRepository.findByClientProfileOrderByTransactionIdDesc(pageable, profile);
    }

    @Override

    public Page<Transaction> findByDepartmentOrderByTransactionIdDesc(Pageable pageable, Department department) {
        return bizRepository.findByDepartmentOrderByTransactionIdDesc(pageable, department);
    }

    @Override

    public List<Transaction> findByTransactionYear(int year) {
        return bizRepository.findByTransactionYear(year);
    }

    @Override

    public Page<Transaction> findAllOrderByTransactionIdDesc(Pageable pageable) {
        return bizRepository.findAllByOrderByTransactionIdDesc(pageable);
    }

    @Override

    public List<Transaction> findAllByOrderByTransactionIdDesc() {
        return bizRepository.findAllByOrderByTransactionIdDesc();
    }

    @Override
    public long countByClientProfileAndPostedMonthAndPostedYear(ClientProfile profile, String transactionMonth, int year) {
        return bizRepository.countByClientProfileAndPostedMonthAndPostedYear(profile, transactionMonth, year);
    }

    @Override
    public long countByDepartmentAndPostedMonthAndPostedYear(Department department, String transactionMonth, int year) {
        return bizRepository.countByDepartmentAndPostedMonthAndPostedYear(department, transactionMonth, year);
    }

    @Override
    public long countByClientProfileAndTransactionMonthAndTransactionYear(ClientProfile profile, String transactionMonth, int year) {
        return bizRepository.countByClientProfileAndTransactionMonthAndTransactionYear(profile, transactionMonth, year);
    }

    @Override
    public long countByDepartmentAndTransactionMonthAndTransactionYear(Department department, String transactionMonth, int year) {
        return bizRepository.countByDepartmentAndTransactionMonthAndTransactionYear(department, transactionMonth, year);
    }

    @Override

    public List<Transaction> findByClientProfileAndTransactionMonthAndTransactionYearAndTransactionDispute(ClientProfile profile, String transactionMonth, int year, boolean status) {
        return bizRepository.findByClientProfileAndTransactionMonthAndTransactionYearAndTransactionDispute(profile, transactionMonth, year, status);
    }

    @Override

    public List<Transaction> findByDepartmentAndTransactionMonthAndTransactionYearAndTransactionDispute(Department department, String transactionMonth, int year, boolean status) {
        return bizRepository.findByDepartmentAndTransactionMonthAndTransactionYearAndTransactionDispute(department, transactionMonth, year, status);
    }

    @Override
    public Page<Transaction> findByClientProfileAndDepartmentAndTransactionMonthAndTransactionYearOrderByTransactionIdDesc(Pageable page, ClientProfile profile, Department department, String transactionMonth, int year) {
        return this.bizRepository.findByClientProfileAndDepartmentAndTransactionMonthAndTransactionYearOrderByTransactionIdDesc(page, profile, department, transactionMonth, year);
    }

    @Override
    public Page<Transaction> findByClientProfileAndTransactionMonthAndTransactionYearOrderByTransactionIdDesc(Pageable page, ClientProfile profile, String transactionMonth, int year) {
        return this.bizRepository.findByClientProfileAndTransactionMonthAndTransactionYearOrderByTransactionIdDesc(page, profile, transactionMonth, year);
    }

    @Override
    public Page<Transaction> findByDepartmentAndTransactionMonthAndTransactionYearOrderByTransactionIdDesc(Pageable page, Department profile, String transactionMonth, int year) {
        return this.bizRepository.findByDepartmentAndTransactionMonthAndTransactionYearOrderByTransactionIdDesc(page, profile, transactionMonth, year);
    }

    @Override
    public Page<Transaction> findByClientProfileAndPostedMonthAndPostedYearOrderByTransactionIdDesc(Pageable page, ClientProfile profile, String transactionMonth, int year) {
        return this.bizRepository.findByClientProfileAndPostedMonthAndPostedYearOrderByTransactionIdDesc(page, profile, transactionMonth, year);
    }

    @Override
    public Page<Transaction> findByDepartmentAndPostedMonthAndPostedYearOrderByTransactionIdDesc(Pageable page, Department profile, String transactionMonth, int year) {
        return this.bizRepository.findByDepartmentAndPostedMonthAndPostedYearOrderByTransactionIdDesc(page, profile, transactionMonth, year);
    }

    @Override
    public List<Transaction> findByClientProfileAndTransactionDateBetween(ClientProfile paramClientProfile, Date publicationTimeStart, Date publicationTimeEnd) {
        return this.bizRepository.findByClientProfileAndTransactionDateBetween(paramClientProfile, publicationTimeStart, publicationTimeEnd);
    }

    @Override
    public List<Transaction> findByDepartmentAndTransactionDateBetween(Department paramDepartment, Date publicationTimeStart, Date publicationTimeEnd) {
        return this.bizRepository.findByDepartmentAndTransactionDateBetween(paramDepartment, publicationTimeStart, publicationTimeEnd);
    }

    @Override
    public Page<Transaction> findByClientProfileAndTransactionDateBetween(Pageable paramPageable, ClientProfile paramClientProfile, Date publicationTimeStart, Date publicationTimeEnd) {
        return this.bizRepository.findByClientProfileAndTransactionDateBetween(paramPageable, paramClientProfile, publicationTimeStart, publicationTimeEnd);
    }

    @Override
    public Page<Transaction> findByDepartmentAndTransactionDateBetween(Pageable paramPageable, Department paramDepartment, Date publicationTimeStart, Date publicationTimeEnd) {
        return this.bizRepository.findByDepartmentAndTransactionDateBetween(paramPageable, paramDepartment, publicationTimeStart, publicationTimeEnd);
    }

    @Override
    public List<Transaction> findByClientProfileAndPostedDateBetween(ClientProfile paramClientProfile, Date publicationTimeStart, Date publicationTimeEnd) {
        return this.bizRepository.findByClientProfileAndPostedDateBetween(paramClientProfile, publicationTimeStart, publicationTimeEnd);
    }

    @Override
    public List<Transaction> findByDepartmentAndPostedDateBetween(Department paramDepartment, Date publicationTimeStart, Date publicationTimeEnd) {
        return this.bizRepository.findByDepartmentAndPostedDateBetween(paramDepartment, publicationTimeStart, publicationTimeEnd);
    }

    @Override
    public Page<Transaction> findByClientProfileAndPostedDateBetween(Pageable paramPageable, ClientProfile paramClientProfile, Date publicationTimeStart, Date publicationTimeEnd) {
        return this.bizRepository.findByClientProfileAndPostedDateBetween(paramPageable, paramClientProfile, publicationTimeStart, publicationTimeEnd);
    }

    @Override
    public Page<Transaction> findByDepartmentAndPostedDateBetween(Pageable paramPageable, Department paramDepartment, Date publicationTimeStart, Date publicationTimeEnd) {
        return this.bizRepository.findByDepartmentAndPostedDateBetween(paramPageable, paramDepartment, publicationTimeStart, publicationTimeEnd);
    }

    @Override
    public List<Transaction> findByDepartmentAndPostedMonthAndPostedYearOrderByTransactionIdDesc(Department department, String paramString, int paramInt) {
        return this.bizRepository.findByDepartmentAndPostedMonthAndPostedYearOrderByTransactionIdDesc(department, paramString, paramInt);
    }

    @Override
    public List<Transaction> findByClientProfileAndPostedMonthAndPostedYearOrderByTransactionIdDesc(ClientProfile profile, String paramString, int paramInt) {
        return this.bizRepository.findByClientProfileAndPostedMonthAndPostedYearOrderByTransactionIdDesc(profile, paramString, paramInt);
    }

    @Override
    public long countByClientProfileAndPostedDateBetween(ClientProfile paramClientProfile, Date publicationTimeStart, Date publicationTimeEnd) {
       return bizRepository.countByClientProfileAndPostedDateBetween(paramClientProfile, publicationTimeStart, publicationTimeEnd);
    }

    @Override
    public long countByDepartmentAndPostedDateBetween(Department paramDepartment, Date publicationTimeStart, Date publicationTimeEnd) {
       return bizRepository.countByDepartmentAndPostedDateBetween(paramDepartment, publicationTimeStart, publicationTimeEnd);
    }

    @Override
    public List<Transaction> findByExitDateTimeAndExitLaneAndVehicle(Date paramDate, String paramString1, String paramString2, Vehicle paramVehicle) {
   return bizRepository.findByExitDateTimeAndExitLaneAndVehicle(paramDate, paramString1, paramString2, paramVehicle);
    }

    @Override
    public long countByDepartmentAndPostedMonthAndPostedYearOrderByTransactionIdDesc(Department department, String month, int year) {
        return bizRepository.countByDepartmentAndPostedMonthAndPostedYearOrderByTransactionIdDesc(department, month, year);
    }

    @Override
    public long countByClientProfileAndPostedMonthAndPostedYearOrderByTransactionIdDesc(ClientProfile profile, String month, int year) {
        return bizRepository.countByClientProfileAndPostedMonthAndPostedYearOrderByTransactionIdDesc(profile, month, year);
    }

    @Override
    public long countTransactions(ClientProfile profile, String month, Integer year) {
      return bizRepository.countTransactions(profile, month, year);
    }

}
