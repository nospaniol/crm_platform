package com.crm.repository;

import com.crm.model.Spending;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.Vehicle;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "spendingss", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "spendingss", key = "#root.target.REDIS_KEY")
public interface SpendingRepository extends MongoRepository<Spending, Long> {

    public static final String REDIS_KEY = "spendingstokens";

    Spending findBySpendingsId(Long id);

    Spending findByVehicleAndSpendingMonthAndSpendingYear(Vehicle vehicle, String spendingMonth, int spendingYear);

    List<Spending> findTop10ByClientProfileOrderBySpendingsIdDesc(ClientProfile clientProfile);

    List<Spending> findByClientProfile(ClientProfile clientProfile);

    List<Spending> findByDepartment(Department department);

    List<Spending> findByClientProfileOrderBySpendingsIdDesc(ClientProfile clientProfile);

    List<Spending> findByDepartmentOrderBySpendingsIdDesc(Department department);

    List<Spending> findTop10ByDepartmentOrderBySpendingsIdDesc(Department department);

    List<Spending> findBySpendingMonthAndSpendingYearOrderBySpendingsIdDesc(String spendingMonth, int spendingYear);

    List<Spending> findByClientProfileAndSpendingMonthAndSpendingYearOrderBySpendingsIdDesc(ClientProfile profile, String spendingMonth, int spendingYear);

    List<Spending> findByDepartmentAndSpendingMonthAndSpendingYear(Department profile, String spendingMonth, int spendingYear);

    List<Spending> findByVehicle(Vehicle vehicle);

    List<Spending> findTop10ByOrderBySpendingsIdDesc();

    List<Spending> findByDepartmentAndSpendingMonthAndSpendingYearOrderBySpendingsIdDesc(Department profile, String spendingMonth, int spendingYear);

    List<Spending> findByClientProfileAndSpendingMonthAndSpendingYear(Sort sort, ClientProfile profile, String spendingMonth, int spendingYear);

}
