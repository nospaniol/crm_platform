package com.crm.service;

import com.crm.generic.GenericService;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.TransactionFile;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionFileService extends GenericService<TransactionFile> {

    public Page<TransactionFile> findAllByOrderByTransactionFileIdDesc(Pageable pageable);

    public long countByClientProfile(ClientProfile profile);

    public long countByClientProfileAndDepartment(ClientProfile profile, Department department);

    public long countByTransactionDate(Date transactionDate);

    public TransactionFile findByTransactionFileId(Long id);

    public List<TransactionFile> findTop10ByOrderByTransactionFileIdDesc();

    public List<TransactionFile> findBottom10ByOrderByTransactionFileIdDesc();

    public List<TransactionFile> findByClientProfileAndDepartment(ClientProfile profile, Department department);

    public List<TransactionFile> findByClientProfile(ClientProfile profile);

    public List<TransactionFile> findTop10ByClientProfileAndDepartment(ClientProfile profile, Department department);

    public List<TransactionFile> findTop10ByClientProfile(ClientProfile profile);

    public List<TransactionFile> findBottom10ByClientProfileAndDepartment(ClientProfile profile, Department department);

    public List<TransactionFile> findBottom10ByClientProfile(ClientProfile profile);

    public long countAll();

    public List<TransactionFile> getPaginatedData(Integer pageNo, Integer pageSize);

    public List<TransactionFile> getSortedPaginatedData(Integer pageNo, Integer pageSize, String sortBy);

    public List<TransactionFile> findByTransactionDate(Date transacationDate);
}
