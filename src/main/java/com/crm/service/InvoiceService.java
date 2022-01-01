package com.crm.service;

import com.crm.generic.GenericService;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.model.Invoice;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvoiceService extends GenericService<Invoice> {

    public long countByClientProfileAndInvoiceMonthAndInvoiceYearAndInvoiceStat(ClientProfile profile, String invoiceMonth, int invoiceYear, String status);

    public long countByClientProfileAndInvoiceMonthAndInvoiceYear(ClientProfile profile, String invoiceMonth, int invoiceYear);

    public long countByDepartmentAndInvoiceMonthAndInvoiceYearAndInvoiceStat(Department department, String invoiceMonth, int invoiceYear, String status);

    public long countByDepartmentAndInvoiceMonthAndInvoiceYear(Department department, String invoiceMonth, int invoiceYear);

    Invoice findByInvoiceId(Long id);

    List<Invoice> findTop10ByClientProfileOrderByInvoiceIdDesc(ClientProfile clientProfile);

    List<Invoice> findByClientProfile(ClientProfile clientProfile);

    List<Invoice> findByDepartment(Department department);

    List<Invoice> findByClientProfileOrderByInvoiceIdDesc(ClientProfile clientProfile);

    List<Invoice> findByDepartmentOrderByInvoiceIdDesc(Department department);

    List<Invoice> findTop10ByDepartmentOrderByInvoiceIdDesc(Department department);

    List<Invoice> findByClientProfileAndInvoiceStat(ClientProfile clientProfile, String invoiceStat);

    List<Invoice> findByDepartmentAndInvoiceStat(Department department, String invoiceStat);

    List<Invoice> findTop10ByOrderByInvoiceIdDesc();

    List<Invoice> findByInvoiceYearOrderByInvoiceIdDesc(int invoiceYear);

    List<Invoice> findByInvoiceYearAndClientProfileOrderByInvoiceIdDesc(int invoiceYear, ClientProfile clientProfile);

    List<Invoice> findByInvoiceYearAndDepartmentOrderByInvoiceIdDesc(int invoiceYear, Department department);

    List<Invoice> findByInvoiceMonthAndInvoiceYearOrderByInvoiceIdDesc(String invoiceMonth, int invoiceYear);

    List<Invoice> findByInvoiceMonthAndInvoiceYearAndClientProfileOrderByInvoiceIdDesc(String invoiceMonth, int invoiceYear, ClientProfile clientProfile);

    List<Invoice> findByInvoiceMonthAndInvoiceYearAndDepartmentOrderByInvoiceIdDesc(String invoiceMonth, int invoiceYear, Department department);

    List<Invoice> findByClientProfileAndInvoiceMonthAndInvoiceYearOrderByInvoiceIdDesc(ClientProfile profile, String invoiceMonth, int invoiceYear);

    List<Invoice> findByDepartmentAndInvoiceMonthAndInvoiceYearOrderByInvoiceIdDesc(Department profile, String invoiceMonth, int invoiceYear);

    public Page<Invoice> findAll(Pageable pageable);

    public long countAll();

    Page<Invoice> findTop10ByClientProfileOrderByInvoiceIdDesc(Pageable pageable, ClientProfile clientProfile);

    Page<Invoice> findByClientProfile(Pageable pageable, ClientProfile clientProfile);

    Page<Invoice> findByDepartment(Pageable pageable, Department department);

    Page<Invoice> findByClientProfileOrderByInvoiceIdDesc(Pageable pageable, ClientProfile clientProfile);

    Page<Invoice> findByDepartmentOrderByInvoiceIdDesc(Pageable pageable, Department department);

    Page<Invoice> findTop10ByDepartmentOrderByInvoiceIdDesc(Pageable pageable, Department department);

    Page<Invoice> findByClientProfileAndInvoiceStat(Pageable pageable, ClientProfile clientProfile, String invoiceStat);

    Page<Invoice> findByDepartmentAndInvoiceStat(Pageable pageable, Department department, String invoiceStat);

    Page<Invoice> findTop10ByOrderByInvoiceIdDesc(Pageable pageable);

    Page<Invoice> findByInvoiceYearOrderByInvoiceIdDesc(Pageable pageable, int invoiceYear);

    Page<Invoice> findByInvoiceYearAndClientProfileOrderByInvoiceIdDesc(Pageable pageable, int invoiceYear, ClientProfile clientProfile);

    Page<Invoice> findByInvoiceYearAndDepartmentOrderByInvoiceIdDesc(Pageable pageable, int invoiceYear, Department department);

    Page<Invoice> findByInvoiceMonthAndInvoiceYearOrderByInvoiceIdDesc(Pageable pageable, String invoiceMonth, int invoiceYear);

    Page<Invoice> findByInvoiceMonthAndInvoiceYearAndClientProfileOrderByInvoiceIdDesc(Pageable pageable, String invoiceMonth, int invoiceYear, ClientProfile clientProfile);

    Page<Invoice> findByInvoiceMonthAndInvoiceYearAndDepartmentOrderByInvoiceIdDesc(Pageable pageable, String invoiceMonth, int invoiceYear, Department department);

    Page<Invoice> findByClientProfileAndInvoiceMonthAndInvoiceYearOrderByInvoiceIdDesc(Pageable pageable, ClientProfile profile, String invoiceMonth, int invoiceYear);

    Page<Invoice> findByDepartmentAndInvoiceMonthAndInvoiceYearOrderByInvoiceIdDesc(Pageable pageable, Department profile, String invoiceMonth, int invoiceYear);

}
