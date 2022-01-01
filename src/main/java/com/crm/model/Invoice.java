package com.crm.model;

import java.io.Serializable;
import java.util.Date;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "invoices")
public class Invoice extends GenericInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "invoice_sequence";
    @DBRef
    private ClientProfile clientProfile;

    @DBRef
    private Department department;

    @DBRef
    private FeeType feeType;
    @Id
    private Long invoiceId;
    private Date invoiceDate;
    private Date paidDate;
    private Double invoiceAmount;
    private Double tollAmount;
    private Double feeAmount;
    private Double totalPaid;
    private Binary excelFile;
    private Binary pdfFile;
    private String invoiceStat;
    private String invoiceMonth;
    private int invoiceYear;
    private String paidMonth;
    private int paidYear;

    public ClientProfile getClientProfile() {
        return clientProfile;
    }

    public void setClientProfile(ClientProfile clientProfile) {
        this.clientProfile = clientProfile;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public FeeType getFeeType() {
        return feeType;
    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public Double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(Double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public Double getTollAmount() {
        return tollAmount;
    }

    public void setTollAmount(Double tollAmount) {
        this.tollAmount = tollAmount;
    }

    public Double getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(Double feeAmount) {
        this.feeAmount = feeAmount;
    }

    public Double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(Double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public Binary getExcelFile() {
        return excelFile;
    }

    public void setExcelFile(Binary excelFile) {
        this.excelFile = excelFile;
    }

    public Binary getPdfFile() {
        return pdfFile;
    }

    public void setPdfFile(Binary pdfFile) {
        this.pdfFile = pdfFile;
    }

    public String getInvoiceStat() {
        return invoiceStat;
    }

    public void setInvoiceStat(String invoiceStat) {
        this.invoiceStat = invoiceStat;
    }

    public String getInvoiceMonth() {
        return invoiceMonth;
    }

    public void setInvoiceMonth(String invoiceMonth) {
        this.invoiceMonth = invoiceMonth;
    }

    public int getInvoiceYear() {
        return invoiceYear;
    }

    public void setInvoiceYear(int invoiceYear) {
        this.invoiceYear = invoiceYear;
    }

    public String getPaidMonth() {
        return paidMonth;
    }

    public void setPaidMonth(String paidMonth) {
        this.paidMonth = paidMonth;
    }

    public int getPaidYear() {
        return paidYear;
    }

    public void setPaidYear(int paidYear) {
        this.paidYear = paidYear;
    }

}
