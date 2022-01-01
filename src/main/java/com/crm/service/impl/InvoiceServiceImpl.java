package com.crm.service.impl;

import com.crm.model.Invoice;
import com.crm.model.ClientProfile;
import com.crm.model.Department;
import com.crm.repository.InvoiceRepository;
import com.crm.service.InvoiceService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository bizRepository;

    @Override
    public Invoice save(Invoice entity) {
        return bizRepository.save(entity);
    }

    @Override
    public Invoice update(Invoice entity) {
        return bizRepository.save(entity);
    }

    @Override
    public void delete(Invoice entity) {
        bizRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        bizRepository.deleteById(id);
    }

    @Override
    public Invoice find(Long id) {
        Optional<Invoice> opdata = bizRepository.findById(id);
        return opdata.get();
    }

    @Override
    public List<Invoice> findAll() {
        return (List<Invoice>) bizRepository.findAll();
    }

    @Override
    public void deleteAll(List<Invoice> users) {
        bizRepository.deleteAll(users);
    }

    @Override
    public Invoice findByInvoiceId(Long id) {
        return bizRepository.findByInvoiceId(id);
    }

    @Override
    public long countAll() {
        return bizRepository.count();
    }

    @Override
    public Page<Invoice> findAll(Pageable pageable) {
        return bizRepository.findAll(pageable);
    }

    @Override
    public List<Invoice> findByClientProfile(ClientProfile clientProfile) {
        return bizRepository.findByClientProfile(clientProfile);
    }

    @Override
    public List<Invoice> findByDepartment(Department department) {
        return bizRepository.findByDepartment(department);
    }

    @Override
    public List<Invoice> findByClientProfileAndInvoiceStat(ClientProfile clientProfile, String invoiceStat) {
        return bizRepository.findByClientProfileAndInvoiceStat(clientProfile, invoiceStat);
    }

    @Override
    public List<Invoice> findByDepartmentAndInvoiceStat(Department department, String invoiceStat) {
        return bizRepository.findByDepartmentAndInvoiceStat(department, invoiceStat);
    }

    @Override
    public List<Invoice> findTop10ByOrderByInvoiceIdDesc() {
        return bizRepository.findTop10ByOrderByInvoiceIdDesc();
    }

    @Override
    public List<Invoice> findTop10ByClientProfileOrderByInvoiceIdDesc(ClientProfile clientProfile) {
        return bizRepository.findTop10ByClientProfileOrderByInvoiceIdDesc(clientProfile);
    }

    @Override
    public List<Invoice> findByClientProfileOrderByInvoiceIdDesc(ClientProfile clientProfile) {
        return bizRepository.findByClientProfileOrderByInvoiceIdDesc(clientProfile);
    }

    @Override
    public List<Invoice> findByDepartmentOrderByInvoiceIdDesc(Department department) {
        return bizRepository.findByDepartmentOrderByInvoiceIdDesc(department);
    }

    @Override
    public List<Invoice> findTop10ByDepartmentOrderByInvoiceIdDesc(Department department) {
        return bizRepository.findTop10ByDepartmentOrderByInvoiceIdDesc(department);
    }

    @Override
    public List<Invoice> findByInvoiceYearOrderByInvoiceIdDesc(int invoiceYear) {
        return bizRepository.findByInvoiceYearOrderByInvoiceIdDesc(invoiceYear);
    }

    @Override
    public List<Invoice> findByInvoiceMonthAndInvoiceYearOrderByInvoiceIdDesc(String invoiceMonth, int invoiceYear) {
        return bizRepository.findByInvoiceMonthAndInvoiceYearOrderByInvoiceIdDesc(invoiceMonth, invoiceYear);
    }

    @Override
    public List<Invoice> findByInvoiceMonthAndInvoiceYearAndClientProfileOrderByInvoiceIdDesc(String invoiceMonth, int invoiceYear, ClientProfile clientProfile) {
        return bizRepository.findByInvoiceMonthAndInvoiceYearAndClientProfileOrderByInvoiceIdDesc(invoiceMonth, invoiceYear, clientProfile);
    }

    @Override
    public List<Invoice> findByInvoiceMonthAndInvoiceYearAndDepartmentOrderByInvoiceIdDesc(String invoiceMonth, int invoiceYear, Department department) {
        return bizRepository.findByInvoiceMonthAndInvoiceYearAndDepartmentOrderByInvoiceIdDesc(invoiceMonth, invoiceYear, department);
    }

    @Override
    public List<Invoice> findByInvoiceYearAndClientProfileOrderByInvoiceIdDesc(int invoiceYear, ClientProfile clientProfile) {
        return bizRepository.findByInvoiceYearAndClientProfileOrderByInvoiceIdDesc(invoiceYear, clientProfile);
    }

    @Override
    public List<Invoice> findByInvoiceYearAndDepartmentOrderByInvoiceIdDesc(int invoiceYear, Department department) {
        return bizRepository.findByInvoiceYearAndDepartmentOrderByInvoiceIdDesc(invoiceYear, department);
    }

    @Override
    public List<Invoice> findByClientProfileAndInvoiceMonthAndInvoiceYearOrderByInvoiceIdDesc(ClientProfile profile, String invoiceMonth, int invoiceYear) {
        return bizRepository.findByClientProfileAndInvoiceMonthAndInvoiceYearOrderByInvoiceIdDesc(profile, invoiceMonth, invoiceYear);
    }

    @Override
    public List<Invoice> findByDepartmentAndInvoiceMonthAndInvoiceYearOrderByInvoiceIdDesc(Department profile, String invoiceMonth, int invoiceYear) {
        return bizRepository.findByDepartmentAndInvoiceMonthAndInvoiceYearOrderByInvoiceIdDesc(profile, invoiceMonth, invoiceYear);
    }

    @Override
    public long countByClientProfileAndInvoiceMonthAndInvoiceYearAndInvoiceStat(ClientProfile profile, String invoiceMonth, int invoiceYear, String status) {
        return bizRepository.countByClientProfileAndInvoiceMonthAndInvoiceYearAndInvoiceStat(profile, invoiceMonth, invoiceYear, status);
    }

    @Override
    public long countByClientProfileAndInvoiceMonthAndInvoiceYear(ClientProfile profile, String invoiceMonth, int invoiceYear) {
        return bizRepository.countByClientProfileAndInvoiceMonthAndInvoiceYear(profile, invoiceMonth, invoiceYear);
    }

    @Override
    public long countByDepartmentAndInvoiceMonthAndInvoiceYearAndInvoiceStat(Department department, String invoiceMonth, int invoiceYear, String status) {
        return bizRepository.countByDepartmentAndInvoiceMonthAndInvoiceYearAndInvoiceStat(department, invoiceMonth, invoiceYear, status);
    }

    @Override
    public long countByDepartmentAndInvoiceMonthAndInvoiceYear(Department department, String invoiceMonth, int invoiceYear) {
        return bizRepository.countByDepartmentAndInvoiceMonthAndInvoiceYear(department, invoiceMonth, invoiceYear);
    }

    @Override
    public Page<Invoice> findByClientProfile(Pageable pageable, ClientProfile clientProfile) {
        return bizRepository.findByClientProfile(pageable, clientProfile);
    }

    @Override
    public Page<Invoice> findByDepartment(Pageable pageable, Department department) {
        return bizRepository.findByDepartment(pageable, department);
    }

    @Override
    public Page<Invoice> findByClientProfileAndInvoiceStat(Pageable pageable, ClientProfile clientProfile, String invoiceStat) {
        return bizRepository.findByClientProfileAndInvoiceStat(pageable, clientProfile, invoiceStat);
    }

    @Override
    public Page<Invoice> findByDepartmentAndInvoiceStat(Pageable pageable, Department department, String invoiceStat) {
        return bizRepository.findByDepartmentAndInvoiceStat(pageable, department, invoiceStat);
    }

    @Override
    public Page<Invoice> findTop10ByOrderByInvoiceIdDesc(Pageable pageable) {
        return bizRepository.findTop10ByOrderByInvoiceIdDesc(pageable);
    }

    @Override
    public Page<Invoice> findTop10ByClientProfileOrderByInvoiceIdDesc(Pageable pageable, ClientProfile clientProfile) {
        return bizRepository.findTop10ByClientProfileOrderByInvoiceIdDesc(pageable, clientProfile);
    }

    @Override
    public Page<Invoice> findByClientProfileOrderByInvoiceIdDesc(Pageable pageable, ClientProfile clientProfile) {
        return bizRepository.findByClientProfileOrderByInvoiceIdDesc(pageable, clientProfile);
    }

    @Override
    public Page<Invoice> findByDepartmentOrderByInvoiceIdDesc(Pageable pageable, Department department) {
        return bizRepository.findByDepartmentOrderByInvoiceIdDesc(pageable, department);
    }

    @Override
    public Page<Invoice> findTop10ByDepartmentOrderByInvoiceIdDesc(Pageable pageable, Department department) {
        return bizRepository.findTop10ByDepartmentOrderByInvoiceIdDesc(pageable, department);
    }

    @Override
    public Page<Invoice> findByInvoiceYearOrderByInvoiceIdDesc(Pageable pageable, int invoiceYear) {
        return bizRepository.findByInvoiceYearOrderByInvoiceIdDesc(pageable, invoiceYear);
    }

    @Override
    public Page<Invoice> findByInvoiceMonthAndInvoiceYearOrderByInvoiceIdDesc(Pageable pageable, String invoiceMonth, int invoiceYear) {
        return bizRepository.findByInvoiceMonthAndInvoiceYearOrderByInvoiceIdDesc(pageable, invoiceMonth, invoiceYear);
    }

    @Override
    public Page<Invoice> findByInvoiceMonthAndInvoiceYearAndClientProfileOrderByInvoiceIdDesc(Pageable pageable, String invoiceMonth, int invoiceYear, ClientProfile clientProfile) {
        return bizRepository.findByInvoiceMonthAndInvoiceYearAndClientProfileOrderByInvoiceIdDesc(pageable, invoiceMonth, invoiceYear, clientProfile);
    }

    @Override
    public Page<Invoice> findByInvoiceMonthAndInvoiceYearAndDepartmentOrderByInvoiceIdDesc(Pageable pageable, String invoiceMonth, int invoiceYear, Department department) {
        return bizRepository.findByInvoiceMonthAndInvoiceYearAndDepartmentOrderByInvoiceIdDesc(pageable, invoiceMonth, invoiceYear, department);
    }

    @Override
    public Page<Invoice> findByInvoiceYearAndClientProfileOrderByInvoiceIdDesc(Pageable pageable, int invoiceYear, ClientProfile clientProfile) {
        return bizRepository.findByInvoiceYearAndClientProfileOrderByInvoiceIdDesc(pageable, invoiceYear, clientProfile);
    }

    @Override
    public Page<Invoice> findByInvoiceYearAndDepartmentOrderByInvoiceIdDesc(Pageable pageable, int invoiceYear, Department department) {
        return bizRepository.findByInvoiceYearAndDepartmentOrderByInvoiceIdDesc(pageable, invoiceYear, department);
    }

    @Override
    public Page<Invoice> findByClientProfileAndInvoiceMonthAndInvoiceYearOrderByInvoiceIdDesc(Pageable pageable, ClientProfile profile, String invoiceMonth, int invoiceYear) {
        return bizRepository.findByClientProfileAndInvoiceMonthAndInvoiceYearOrderByInvoiceIdDesc(pageable, profile, invoiceMonth, invoiceYear);
    }

    @Override
    public Page<Invoice> findByDepartmentAndInvoiceMonthAndInvoiceYearOrderByInvoiceIdDesc(Pageable pageable, Department profile, String invoiceMonth, int invoiceYear) {
        return bizRepository.findByDepartmentAndInvoiceMonthAndInvoiceYearOrderByInvoiceIdDesc(pageable, profile, invoiceMonth, invoiceYear);
    }

}
