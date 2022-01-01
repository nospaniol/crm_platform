package com.crm.service;

import com.crm.generic.GenericService;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.OrderShippingFile;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderShippingFileService extends GenericService<OrderShippingFile> {

    public Page<OrderShippingFile> findAllByOrderByOrderFileIdDesc(Pageable pageable);

    public long countByClientProfile(ClientProfile profile);

    public long countByClientProfileAndDepartment(ClientProfile profile, Department department);

    public long countByOrderDate(Date orderDate);

    public OrderShippingFile findByOrderFileId(Long id);

    public List<OrderShippingFile> findTop10ByOrderByOrderFileIdDesc();

    public List<OrderShippingFile> findBottom10ByOrderByOrderFileIdDesc();

    public List<OrderShippingFile> findByClientProfileAndDepartment(ClientProfile profile, Department department);

    public List<OrderShippingFile> findByClientProfile(ClientProfile profile);

    public List<OrderShippingFile> findTop10ByClientProfileAndDepartment(ClientProfile profile, Department department);

    public List<OrderShippingFile> findTop10ByClientProfile(ClientProfile profile);

    public List<OrderShippingFile> findBottom10ByClientProfileAndDepartment(ClientProfile profile, Department department);

    public List<OrderShippingFile> findBottom10ByClientProfile(ClientProfile profile);

    public List<OrderShippingFile> findByOrderDate(Date transacationDate);
}
