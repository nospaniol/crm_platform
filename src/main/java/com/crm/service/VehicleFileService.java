package com.crm.service;

import com.crm.generic.GenericService;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.VehicleFile;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleFileService extends GenericService<VehicleFile> {

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

    public long countAll();

    public List<VehicleFile> getPaginatedData(Integer pageNo, Integer pageSize);

    public List<VehicleFile> getSortedPaginatedData(Integer pageNo, Integer pageSize, String sortBy);

    public List<VehicleFile> findByVehicleDate(Date transacationDate);
}
