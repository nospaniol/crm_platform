package com.crm.repository;

import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.Transaction;
import com.crm.model.TransactionFile;
import com.crm.model.VehicleFile;
import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
//@Cacheable(value = "transactionFiles", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "transactionFiles", key = "#root.target.REDIS_KEY")
public interface TransactionFileRepository extends MongoRepository<TransactionFile, Long> {

    public static final String REDIS_KEY = "transactionFiletokens";

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

    public List<TransactionFile> findByTransactionDate(Date transacationDate);
}
