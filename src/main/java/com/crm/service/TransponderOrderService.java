package com.crm.service;

import com.crm.generic.GenericService;
import com.crm.repository.*;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.TransponderOrder;
import com.crm.model.VehicleFile;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface TransponderOrderService extends GenericService<TransponderOrder> {

    public long countByClientProfileAndOrderStatus(ClientProfile profile, String status);

    public long countByDepartmentAndOrderStatus(Department department, String status);

    public long countByClientProfile(ClientProfile profile);

    public long countByDepartment(Department department);

    public Page<TransponderOrder> findAllByOrderIdDesc(Pageable pageable);

    public List<TransponderOrder> findAllByOrderIdDesc();

    public TransponderOrder findByOrderId(Long id);

    public TransponderOrder findByClientProfileAndOrderId(ClientProfile profile, Long id);

    public TransponderOrder findByDepartmentAndOrderId(Department profile, Long id);

    public List<TransponderOrder> findByLicensePlateOrderByOrderIdDesc(String plate);

    public List<TransponderOrder> findTop10ByOrderByOrderIdDesc();

    public List<TransponderOrder> findByDepartmentOrderByOrderIdDesc(Department department);

    public List<TransponderOrder> findByClientProfileAndDepartmentOrderByOrderIdDesc(ClientProfile profile, Department department);

    public List<TransponderOrder> findByClientProfileOrderByOrderIdDesc(ClientProfile profile);

    public List<TransponderOrder> findTop10ByClientProfileAndDepartmentOrderByOrderIdDesc(ClientProfile profile, Department department);

    public List<TransponderOrder> findTop10ByClientProfileOrderByOrderIdDesc(ClientProfile profile);

    public Page<TransponderOrder> findByClientProfile(Pageable pageable, ClientProfile profile);

    public Page<TransponderOrder> findByDepartment(Pageable pageable, Department department);

}
