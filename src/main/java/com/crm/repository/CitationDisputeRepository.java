package com.crm.repository;

import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.Citation;
import com.crm.model.CitationDispute;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Repository
//@Cacheable(value = "citationdisputes", key = "#root.target.REDIS_KEY")
//@CacheEvict(value = "citationdisputes", key = "#root.target.REDIS_KEY")
public interface CitationDisputeRepository extends MongoRepository<CitationDispute, Long> {

    public static final String REDIS_KEY = "citationdisputetokens";

    public long countByClientProfile(ClientProfile profile);

    public long countByDepartment(Department department);

    public CitationDispute findByCitation(Citation citation);

    public Page<CitationDispute> findByClientProfile(Pageable pageable, ClientProfile profile);

    public Page<CitationDispute> findByClientProfileAndDisputeStatus(Pageable pageable, ClientProfile profile, String status);

    public Page<CitationDispute> findByDepartment(Pageable pageable, Department department);

    public Page<CitationDispute> findByDepartmentAndDisputeStatus(Pageable pageable, Department department, String status);

    public Page<CitationDispute> findByDisputeStatus(Pageable pageable, String status);

    public CitationDispute findByDisputeId(Long id);

    public List<CitationDispute> findTop10ByOrderByDisputeIdDesc();

    public List<CitationDispute> findByDepartmentOrderByDisputeIdDesc(Department department);

    public List<CitationDispute> findByDisputeStatusOrderByDisputeIdDesc(String status);

    public List<CitationDispute> findByClientProfileAndDisputeStatusOrderByDisputeIdDesc(ClientProfile profile, String status);

    public List<CitationDispute> findByDepartmentAndDisputeStatusOrderByDisputeIdDesc(Department department, String status);

    public List<CitationDispute> findByClientProfileOrderByDisputeIdDesc(ClientProfile profile);

    public List<CitationDispute> findTop10ByClientProfileAndDepartmentOrderByDisputeId(ClientProfile profile, Department department);

    public List<CitationDispute> findTop10ByClientProfileOrderByDisputeIdDesc(ClientProfile profile);

    public Page<CitationDispute> findByOrderByDisputeIdDesc(Pageable pageable);

    public List<CitationDispute> findByOrderByDisputeIdDesc();

}
