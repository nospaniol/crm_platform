package com.crm.service;

import com.crm.generic.GenericService;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.Transaction;
import com.crm.model.TransactionDispute;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionDisputeService extends GenericService<TransactionDispute> {

    public long countByClientProfile(ClientProfile profile);

    public long countByDepartment(Department department);

    public TransactionDispute findByTransaction(Transaction transaction);

    public Page<TransactionDispute> findByClientProfile(Pageable pageable, ClientProfile profile);

    public Page<TransactionDispute> findByClientProfileAndDisputeStatus(Pageable pageable, ClientProfile profile, String status);

    public Page<TransactionDispute> findByDepartment(Pageable pageable, Department department);

    public Page<TransactionDispute> findByDepartmentAndDisputeStatus(Pageable pageable, Department department, String status);

    public Page<TransactionDispute> findByDisputeStatus(Pageable pageable, String status);

    public TransactionDispute findByDisputeId(Long id);

    public List<TransactionDispute> findTop10ByOrderByDisputeIdDesc();

    public List<TransactionDispute> findByDepartmentOrderByDisputeIdDesc(Department department);

    public List<TransactionDispute> findByDisputeStatusOrderByDisputeIdDesc(String status);

    public List<TransactionDispute> findByClientProfileAndDisputeStatusOrderByDisputeIdDesc(ClientProfile profile, String status);

    public List<TransactionDispute> findByDepartmentAndDisputeStatusOrderByDisputeIdDesc(Department department, String status);

    public List<TransactionDispute> findByClientProfileOrderByDisputeIdDesc(ClientProfile profile);

    public List<TransactionDispute> findTop10ByClientProfileAndDepartmentOrderByDisputeId(ClientProfile profile, Department department);

    public List<TransactionDispute> findTop10ByClientProfileOrderByDisputeIdDesc(ClientProfile profile);

    public Page<TransactionDispute> findByOrderByDisputeIdDesc(Pageable pageable);

    public List<TransactionDispute> findByOrderByDisputeIdDesc();
}
