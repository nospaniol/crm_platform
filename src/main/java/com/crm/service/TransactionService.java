package com.crm.service;

import com.crm.generic.GenericService;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.Transaction;
import com.crm.model.Vehicle;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService extends GenericService<Transaction> {

    long countByClientProfile(ClientProfile paramClientProfile);

    long countByDepartment(Department paramDepartment);

    long countByDepartmentAndPostedMonthAndPostedYearOrderByTransactionIdDesc(Department department, String month, int year);

    long countByClientProfileAndPostedMonthAndPostedYearOrderByTransactionIdDesc(ClientProfile profile, String month, int year);

    long countByClientProfileAndPostedMonthAndPostedYear(ClientProfile paramClientProfile, String paramString, int paramInt);

    long countByDepartmentAndPostedMonthAndPostedYear(Department paramDepartment, String paramString, int paramInt);

    long countByClientProfileAndTransactionMonthAndTransactionYear(ClientProfile paramClientProfile, String paramString, int paramInt);

    long countByDepartmentAndTransactionMonthAndTransactionYear(Department paramDepartment, String paramString, int paramInt);

    long countByClientProfileAndPostedDateBetween(ClientProfile paramClientProfile, Date publicationTimeStart, Date publicationTimeEnd);

    long countByDepartmentAndPostedDateBetween(Department paramDepartment, Date publicationTimeStart, Date publicationTimeEnd);

    long countTransactions(ClientProfile profile, String month, Integer year);

    Page<Transaction> findByClientProfileOrderByTransactionIdDesc(Pageable paramPageable, ClientProfile paramClientProfile);

    Page<Transaction> findByDepartmentOrderByTransactionIdDesc(Pageable paramPageable, Department paramDepartment);

    Transaction findByTransactionId(Long paramLong);

    List<Transaction> findAllByOrderByTransactionIdDesc();

    List<Transaction> findByExitDateTimeAndVehicle(String paramString, Vehicle paramVehicle);

    List<Transaction> findByExitDateTimeAndExitLocationAndVehicle(String paramString1, String paramString2, Vehicle paramVehicle);

    List<Transaction> findByExitDateTimeAndExitLaneAndVehicle(Date paramDate, String paramString, Vehicle paramVehicle);

    List<Transaction> findByExitDateTimeAndExitLocationAndExitLaneAndVehicle(Date paramDate, String paramString1, String paramString2, Vehicle paramVehicle);

    List<Transaction> findByExitDateTimeAndExitLaneAndVehicle(Date paramDate, String paramString1, String paramString2, Vehicle paramVehicle);

    List<Transaction> findByClientProfileAndExitDateTimeAndExitLaneAndVehicleAndAmount(ClientProfile paramClientProfile, Date paramDate, String paramString, Vehicle paramVehicle, Double amount);

    List<Transaction> findByClientProfileAndExitDateTimeAndVehicle(ClientProfile paramClientProfile, String paramString, Vehicle paramVehicle);

    List<Transaction> findByClientProfileAndExitDateTimeAndExitLocationAndVehicle(ClientProfile paramClientProfile, String paramString1, String paramString2, Vehicle paramVehicle);

    List<Transaction> findByClientProfileAndExitDateTimeAndExitLaneAndVehicle(ClientProfile paramClientProfile, Date paramDate, String paramString, Vehicle paramVehicle);

    List<Transaction> findByClientProfileAndExitDateTimeAndExitLocationAndExitLaneAndVehicle(ClientProfile paramClientProfile, Date paramDate, String paramString1, String paramString2, Vehicle paramVehicle);

    Page<Transaction> findByVehicle(Pageable paramPageable, Vehicle paramVehicle);

    List<Transaction> findTop10ByOrderByTransactionIdDesc();

    List<Transaction> findByClientProfileAndDepartmentOrderByTransactionIdDesc(ClientProfile paramClientProfile, Department paramDepartment);

    List<Transaction> findByClientProfileOrderByTransactionIdDesc(ClientProfile paramClientProfile);

    List<Transaction> findTop10ByClientProfileAndDepartmentOrderByTransactionId(ClientProfile paramClientProfile, Department paramDepartment);

    List<Transaction> findTop10ByClientProfileOrderByTransactionIdDesc(ClientProfile paramClientProfile);

    List<Transaction> findByTransactionFileId(Long paramLong);

    Page<Transaction> findByOrderByTransactionIdDesc(Pageable paramPageable);

    List<Transaction> findByClientProfileAndTransactionMonthAndTransactionYear(ClientProfile paramClientProfile, String paramString, int paramInt);

    List<Transaction> findByClientProfileAndTransactionMonthAndTransactionYearAndAgency(ClientProfile paramClientProfile, String paramString1, int paramInt, String paramString2);

    List<Transaction> findByClientProfileAndTransactionMonthAndTransactionYearAndVehicle(ClientProfile paramClientProfile, String paramString, int paramInt, Vehicle paramVehicle);

    List<Transaction> findByClientProfileAndTransactionYear(ClientProfile paramClientProfile, int paramInt);

    List<Transaction> findByClientProfileAndTransactionMonth(ClientProfile paramClientProfile, String paramString);

    List<Transaction> findByDepartmentAndTransactionMonthAndTransactionYear(Department paramDepartment, String paramString, int paramInt);

    List<Transaction> findByDepartmentAndTransactionMonthAndTransactionYearAndAgency(Department paramDepartment, String paramString1, int paramInt, String paramString2);

    List<Transaction> findByDepartmentAndTransactionMonthAndTransactionYearAndVehicle(Department paramDepartment, String paramString, int paramInt, Vehicle paramVehicle);

    List<Transaction> findByDepartmentAndTransactionYear(Department paramDepartment, int paramInt);

    List<Transaction> findByDepartmentAndTransactionMonth(Department paramDepartment, String paramString);

    List<Transaction> findByTransactionMonthAndTransactionYear(String paramString, int paramInt);

    List<Transaction> findByTransactionYear(int paramInt);

    List<Transaction> findByOrderByTransactionIdDesc();

    List<Transaction> findByVehicle(Vehicle paramVehicle);

    List<Transaction> findByVehicleAndTransactionDate(Vehicle paramVehicle, Date paramDate);

    long countByTransactionDate(Date paramDate);

    Page<Transaction> findByVehicleAndTransactionDate(Pageable paramPageable, Vehicle paramVehicle, Date paramDate);

    Page<Transaction> findByTransactionDate(Pageable paramPageable, Date paramDate);

    List<Transaction> findByClientProfileAndDepartmentAndTransactionDate(ClientProfile paramClientProfile, Department paramDepartment, Date paramDate);

    List<Transaction> findByTransactionDate(Date paramDate);

    List<Transaction> findByVehicleAndPostedDate(Vehicle paramVehicle, Date paramDate);

    List<Transaction> findByClientProfileAndTransactionDateOrderByTransactionIdDesc(ClientProfile paramClientProfile, Date paramDate);

    List<Transaction> findByClientProfileAndPostedDateOrderByTransactionIdDesc(ClientProfile paramClientProfile, Date paramDate);

    List<Transaction> findByDepartmentAndTransactionDateOrderByTransactionIdDesc(Department paramDepartment, Date paramDate);

    List<Transaction> findByDepartmentAndPostedDate(Department paramDepartment, Date paramDate);

    long countByPostedDate(Date paramDate);

    Page<Transaction> findByVehicleAndPostedDate(Pageable paramPageable, Vehicle paramVehicle, Date paramDate);

    Page<Transaction> findByPostedDate(Pageable paramPageable, Date paramDate);

    List<Transaction> findByPostedDate(Date paramDate);

    long countByClientProfileAndPostedDate(ClientProfile paramClientProfile, Date paramDate);

    long countByDepartmentAndPostedDate(Department paramDepartment, Date paramDate);

    Page<Transaction> findAllOrderByTransactionIdDesc(Pageable paramPageable);

    Page<Transaction> findByClientProfileAndTransactionMonthAndTransactionYearOrderByTransactionIdDesc(Pageable page, ClientProfile profile, String transactionMonth, int year);

    Page<Transaction> findByClientProfileAndPostedMonthAndPostedYearOrderByTransactionIdDesc(Pageable page, ClientProfile profile, String transactionMonth, int year);

    Page<Transaction> findByDepartmentAndTransactionMonthAndTransactionYearOrderByTransactionIdDesc(Pageable page, Department profile, String transactionMonth, int year);

    Page<Transaction> findByDepartmentAndPostedMonthAndPostedYearOrderByTransactionIdDesc(Pageable page, Department profile, String transactionMonth, int year);

    List<Transaction> findByClientProfileAndTransactionMonthAndTransactionYearAndTransactionDispute(ClientProfile paramClientProfile, String paramString, int paramInt, boolean paramBoolean);

    List<Transaction> findByDepartmentAndTransactionMonthAndTransactionYearAndTransactionDispute(Department paramDepartment, String paramString, int paramInt, boolean paramBoolean);

    Page<Transaction> findByClientProfileAndDepartmentAndTransactionMonthAndTransactionYearOrderByTransactionIdDesc(Pageable paramPageable, ClientProfile paramClientProfile, Department paramDepartment, String paramString, int paramInt);

    List<Transaction> findByClientProfileAndTransactionDateBetween(ClientProfile paramClientProfile, Date publicationTimeStart, Date publicationTimeEnd);

    List<Transaction> findByDepartmentAndTransactionDateBetween(Department paramDepartment, Date publicationTimeStart, Date publicationTimeEnd);

    Page<Transaction> findByClientProfileAndTransactionDateBetween(Pageable paramPageable, ClientProfile paramClientProfile, Date publicationTimeStart, Date publicationTimeEnd);

    Page<Transaction> findByDepartmentAndTransactionDateBetween(Pageable paramPageable, Department paramDepartment, Date publicationTimeStart, Date publicationTimeEnd);

    List<Transaction> findByClientProfileAndPostedDateBetween(ClientProfile paramClientProfile, Date publicationTimeStart, Date publicationTimeEnd);

    List<Transaction> findByDepartmentAndPostedDateBetween(Department paramDepartment, Date publicationTimeStart, Date publicationTimeEnd);

    Page<Transaction> findByClientProfileAndPostedDateBetween(Pageable paramPageable, ClientProfile paramClientProfile, Date publicationTimeStart, Date publicationTimeEnd);

    Page<Transaction> findByDepartmentAndPostedDateBetween(Pageable paramPageable, Department paramDepartment, Date publicationTimeStart, Date publicationTimeEnd);

    List<Transaction> findByDepartmentAndPostedMonthAndPostedYearOrderByTransactionIdDesc(Department department, String paramString, int paramInt);

    List<Transaction> findByClientProfileAndPostedMonthAndPostedYearOrderByTransactionIdDesc(ClientProfile profile, String paramString, int paramInt);

}
