package com.crm.repository;

import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.Fullfillment;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FullfillmentRepository extends MongoRepository<Fullfillment, Long> {

    public long countByClientProfileAndOrderStatus(ClientProfile profile, String status);

    public long countByDepartmentAndOrderStatus(Department department, String status);

    public long countByClientProfile(ClientProfile profile);

    public long countByDepartment(Department department);

    public Page<Fullfillment> findAllByOrderByOrderIdDesc(Pageable pageable);

    public List<Fullfillment> findAllByOrderByOrderIdDesc();

    public Fullfillment findByOrderId(Long id);

    public Fullfillment findByClientProfileAndOrderId(ClientProfile profile, Long id);

    public Fullfillment findByDepartmentAndOrderId(Department profile, Long id);

    public List<Fullfillment> findByLicensePlateOrderByOrderIdDesc(String plate);

    public List<Fullfillment> findTop10ByOrderByOrderIdDesc();

    public List<Fullfillment> findByDepartmentOrderByOrderIdDesc(Department department);

    public List<Fullfillment> findByClientProfileAndDepartmentOrderByOrderIdDesc(ClientProfile profile, Department department);

    public List<Fullfillment> findByClientProfileOrderByOrderIdDesc(ClientProfile profile);

    public List<Fullfillment> findTop10ByClientProfileAndDepartmentOrderByOrderIdDesc(ClientProfile profile, Department department);

    public List<Fullfillment> findTop10ByClientProfileOrderByOrderIdDesc(ClientProfile profile);

    public Page<Fullfillment> findByClientProfile(Pageable pageable, ClientProfile profile);

    public Page<Fullfillment> findByDepartment(Pageable pageable, Department department);

}
