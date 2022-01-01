package com.crm.service;

import com.crm.model.Vehicle;
import com.crm.generic.GenericService;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.VehicleFile;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleService extends GenericService<Vehicle> {

    public List<Vehicle> findByVehicleFile(VehicleFile vehicleFileId);

    public void deleteInBatch(Iterable<Vehicle> entities);

    public List<Vehicle> findByLicensePlateOrderByVehicleIdDesc(String plate);

    public long countByClientProfileAndVehicleStatus(ClientProfile profile, String status);

    public long countByDepartmentAndVehicleStatus(Department department, String status);

    public long countByClientProfileAndVinNotAndStateNot(ClientProfile profile, String tolltag, String state);

    public long countByDepartmentAndVinNotAndStateNot(Department department, String tolltag, String state);

    public Page<Vehicle> getSortedPaginatedData(Integer pageNo, Integer pageSize, String sortBy);

    public Page<Vehicle> findAll(Pageable pageable);

    public Vehicle findByVin(String vin);

    public Page<Vehicle> findAllByOrderByVehicleIdDesc(Pageable pageable);

    public List<Vehicle> findAllByOrderByVehicleIdDesc();

    public long countByDepartment(Department department);

    public Page<Vehicle> getPaginatedData(Integer pageNo, Integer pageSize);

    public Page<Vehicle> getPageable(Pageable pageable);

    public Vehicle findByVehicleId(Long id);

    public Vehicle findByLicensePlate(String plate);

    List<Vehicle> findByClientProfileAndVinLike(ClientProfile profile, String vin);

    List<Vehicle> findByClientProfileAndTollTagIdLike(ClientProfile profile, String tollTagId);

    List<Vehicle> findByClientProfileAndLicensePlateLike(ClientProfile profile, String licensePlate);

    public List<Vehicle> findByTollTagId(String tolltag);

    public List<Vehicle> findTop10ByOrderByVehicleIdDesc();

    public List<Vehicle> findByClientProfileAndDepartmentOrderByVehicleIdDesc(ClientProfile profile, Department department);

    public List<Vehicle> findByClientProfileOrderByVehicleIdDesc(ClientProfile profile);

    public List<Vehicle> findTop10ByClientProfileAndDepartmentOrderByVehicleIdDesc(ClientProfile profile, Department department);

    public List<Vehicle> findTop10ByClientProfileOrderByVehicleIdDesc(ClientProfile profile);

    public long countAll();

    public long countByClientProfile(ClientProfile profile);

    public Page<Vehicle> findByClientProfile(Pageable pageable, ClientProfile profile);

    public Page<Vehicle> findByDepartment(Pageable pageable, Department department);

    public Page<Vehicle> findByClientProfileAndTollTagIdNot(Pageable pageable, ClientProfile profile, String tolltag);

    public Page<Vehicle> findByDepartmentAndTollTagIdNot(Pageable pageable, Department department, String tolltag);

    public Page<Vehicle> findByClientProfileAndVinNot(Pageable pageable, ClientProfile profile, String tolltag);

    public Page<Vehicle> findByDepartmentAndVinNot(Pageable pageable, Department department, String tolltag);

    public Page<Vehicle> findByClientProfileAndVinNotAndStateNot(Pageable pageable, ClientProfile profile, String tolltag, String ortolltag);

    public Page<Vehicle> findByDepartmentAndVinNotAndStateNot(Pageable pageable, Department department, String tolltag, String ortolltag);

    public Page<Vehicle> findByClientProfileAndState(Pageable pageable, ClientProfile profile, String state);

    public Page<Vehicle> findByClientProfileAndVinNotAndStateNotOrStateNot(Pageable pageable, ClientProfile profile, String tolltag, String state, String state1);

    public Page<Vehicle> findByDepartmentAndState(Pageable pageable, Department department, String state);

    public Page<Vehicle> findByDepartmentAndVinNotAndStateNotOrStateNot(Pageable pageable, Department department, String tolltag, String state, String state1);

}
