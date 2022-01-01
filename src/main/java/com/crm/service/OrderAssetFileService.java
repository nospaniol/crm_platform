package com.crm.service;

import com.crm.generic.GenericService;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.OrderAssetFile;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderAssetFileService extends GenericService<OrderAssetFile> {

    public Page<OrderAssetFile> findAllByOrderByOrderFileIdDesc(Pageable pageable);

    public long countByClientProfile(ClientProfile profile);

    public long countByClientProfileAndDepartment(ClientProfile profile, Department department);

    public long countByOrderDate(Date orderDate);

    public OrderAssetFile findByOrderFileId(Long id);

    public List<OrderAssetFile> findTop10ByOrderByOrderFileIdDesc();

    public List<OrderAssetFile> findBottom10ByOrderByOrderFileIdDesc();

    public List<OrderAssetFile> findByClientProfileAndDepartment(ClientProfile profile, Department department);

    public List<OrderAssetFile> findByClientProfile(ClientProfile profile);

    public List<OrderAssetFile> findTop10ByClientProfileAndDepartment(ClientProfile profile, Department department);

    public List<OrderAssetFile> findTop10ByClientProfile(ClientProfile profile);

    public List<OrderAssetFile> findBottom10ByClientProfileAndDepartment(ClientProfile profile, Department department);

    public List<OrderAssetFile> findBottom10ByClientProfile(ClientProfile profile);

    public List<OrderAssetFile> findByOrderDate(Date transacationDate);
}
