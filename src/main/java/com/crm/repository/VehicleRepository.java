package com.crm.repository;

import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.Vehicle;
import com.crm.model.VehicleFile;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.Query;

@Repository
//@Cacheable(value = "vehicles", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "vehicles", key = "#root.target.REDIS_KEY")
public interface VehicleRepository extends MongoRepository<Vehicle, Long> {

    public static final String REDIS_KEY = "vehicletokens";

    public long countByClientProfileAndVehicleStatus(ClientProfile profile, String status);

    public long countByDepartmentAndVehicleStatus(Department department, String status);

    public long countByClientProfile(ClientProfile profile);

    public long countByDepartment(Department department);
    
    public long countByClientProfileAndVinNotAndStateNot(ClientProfile profile, String tolltag, String state);

    public long countByDepartmentAndVinNotAndStateNot(Department department, String tolltag, String state);

    public List<Vehicle> findByVehicleFile(VehicleFile vehicleFileId);

    public Page<Vehicle> findAllByOrderByVehicleIdDesc(Pageable pageable);

    public List<Vehicle> findAllByOrderByVehicleIdDesc();

    public Vehicle findByVehicleId(Long id);

    public Vehicle findByLicensePlate(String plate);

    public List<Vehicle> findByLicensePlateOrderByVehicleIdDesc(String plate);

    public List<Vehicle> findByTollTagId(String tolltag);
    
    List<Vehicle> findByClientProfileAndVinIgnoreCaseLike(ClientProfile profile, String vin);

    List<Vehicle> findByClientProfileAndTollTagIdIgnoreCaseLike(ClientProfile profile, String tollTagId);

    List<Vehicle> findByClientProfileAndLicensePlateIgnoreCaseLike(ClientProfile profile, String licensePlate);

    public Vehicle findByVin(String vin);

    public List<Vehicle> findTop10ByOrderByVehicleIdDesc();

    public List<Vehicle> findByDepartmentOrderByVehicleIdDesc(Department department);

    public List<Vehicle> findByClientProfileAndDepartmentOrderByVehicleIdDesc(ClientProfile profile, Department department);

    public List<Vehicle> findByClientProfileOrderByVehicleIdDesc(ClientProfile profile);

    public List<Vehicle> findTop10ByClientProfileAndDepartmentOrderByVehicleIdDesc(ClientProfile profile, Department department);

    public List<Vehicle> findTop10ByClientProfileOrderByVehicleIdDesc(ClientProfile profile);

    public Page<Vehicle> findByClientProfile(Pageable pageable, ClientProfile profile);

    public Page<Vehicle> findByDepartment(Pageable pageable, Department department);

    public Page<Vehicle> findByClientProfileAndTollTagIdNot(Pageable pageable, ClientProfile profile, String tolltag);

    public Page<Vehicle> findByDepartmentAndTollTagIdNot(Pageable pageable, Department department, String tolltag);

    public Page<Vehicle> findByClientProfileAndVinNot(Pageable pageable, ClientProfile profile, String tolltag);

    public Page<Vehicle> findByDepartmentAndVinNot(Pageable pageable, Department department, String tolltag);

    public Page<Vehicle> findByClientProfileAndVinNotAndStateNot(Pageable pageable, ClientProfile profile, String tolltag, String ortolltag);

    public Page<Vehicle> findByDepartmentAndVinNotAndStateNot(Pageable pageable, Department department, String tolltag, String ortolltag);

    public Page<Vehicle> findByClientProfileAndState(Pageable pageable, ClientProfile profile,String state);

    public Page<Vehicle> findByClientProfileAndVinNotAndStateNotOrStateNot(Pageable pageable, ClientProfile profile, String tolltag, String state, String state1);

    public Page<Vehicle> findByDepartmentAndState(Pageable pageable, Department department,String state);
    
    public Page<Vehicle> findByDepartmentAndVinNotAndStateNotOrStateNot(Pageable pageable, Department department, String tolltag, String state, String state1);

}
