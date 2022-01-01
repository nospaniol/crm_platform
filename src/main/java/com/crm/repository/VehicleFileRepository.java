package com.crm.repository;

import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.VehicleFile;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "vehicleFiles", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "vehicleFiles", key = "#root.target.REDIS_KEY")
public interface VehicleFileRepository extends MongoRepository<VehicleFile, Long> {

    public static final String REDIS_KEY = "vehicleFiletokens";

    public Page<VehicleFile> findAllByOrderByVehicleFileIdDesc(Pageable pageable);

    public long countByClientProfile(ClientProfile profile);

    public long countByClientProfileAndDepartment(ClientProfile profile, Department department);

    public long countByVehicleDate(Date vehicleDate);

    public VehicleFile findByVehicleFileId(Long id);

    public List<VehicleFile> findTop10ByOrderByVehicleFileIdDesc();

    public List<VehicleFile> findBottom10ByOrderByVehicleFileIdDesc();

    public List<VehicleFile> findByClientProfileAndDepartment(ClientProfile profile, Department department);

    public List<VehicleFile> findByClientProfile(ClientProfile profile);

    public List<VehicleFile> findTop10ByClientProfileAndDepartment(ClientProfile profile, Department department);

    public List<VehicleFile> findTop10ByClientProfile(ClientProfile profile);

    public List<VehicleFile> findBottom10ByClientProfileAndDepartment(ClientProfile profile, Department department);

    public List<VehicleFile> findBottom10ByClientProfile(ClientProfile profile);

    public List<VehicleFile> findByVehicleDate(Date transacationDate);
}
