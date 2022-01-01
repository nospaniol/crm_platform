package com.crm.repository;

import com.crm.model.Citation;
import com.crm.model.CitationType;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.Vehicle;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "citations", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "citations", key = "#root.target.REDIS_KEY")
public interface CitationRepository extends MongoRepository<Citation, Long> {

    public static final String REDIS_KEY = "citationtokens";

    Citation findByCitationId(Long id);

    public long countByClientProfileAndCitationMonthAndCitationYearAndCitationType(ClientProfile profile, String citationMonth, int citationYear, CitationType type);

    public long countByDepartmentAndCitationMonthAndCitationYearAndCitationType(Department department, String citationMonth, int citationYear, CitationType type);

    List<Citation> findTop10ByClientProfileOrderByCitationIdDesc(ClientProfile clientProfile);

    List<Citation> findByClientProfile(ClientProfile clientProfile);

    List<Citation> findByDepartment(Department department);

    List<Citation> findByClientProfileOrderByCitationIdDesc(ClientProfile clientProfile);

    List<Citation> findByDepartmentOrderByCitationIdDesc(Department department);

    List<Citation> findTop10ByDepartmentOrderByCitationIdDesc(Department department);

    List<Citation> findByClientProfileAndCitationStatus(ClientProfile clientProfile, String citationStatus);

    List<Citation> findByDepartmentAndCitationStatus(Department department, String citationStatus);

    List<Citation> findByCitationMonthAndCitationYearOrderByCitationIdDesc(String citationMonth, int citationYear);

    List<Citation> findByClientProfileAndCitationMonthAndCitationYearOrderByCitationIdDesc(ClientProfile profile, String citationMonth, int citationYear);

    List<Citation> findByDepartmentAndCitationMonthAndCitationYearOrderByCitationIdDesc(Department profile, String citationMonth, int citationYear);

    List<Citation> findByClientProfileAndCitationMonthAndCitationYearAndCitationTypeOrderByCitationIdDesc(ClientProfile profile, String citationMonth, int citationYear, CitationType type);

    List<Citation> findByDepartmentAndCitationMonthAndCitationYearAndCitationTypeOrderByCitationIdDesc(Department profile, String citationMonth, int citationYear, CitationType type);

    List<Citation> findByVehicle(Vehicle vehicle);

    List<Citation> findByVehicleAndCitationStatus(Vehicle vehicle, String status);

    List<Citation> findTop10ByOrderByCitationIdDesc();
}
