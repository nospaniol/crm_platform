package com.crm.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class TransactionDispute implements Serializable {

    private ClientProfile clientProfile;

    private Department department;

    private Transaction transaction;

    private Long disputeId;
    private String disputeStatus;
    private String dispute;
    protected Date creationDate;
    protected Date updatedDate;
    private String creationTime;
    private String updatedTime;
    private boolean status;

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

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Long getDisputeId() {
        return disputeId;
    }

    public void setDisputeId(Long disputeId) {
        this.disputeId = disputeId;
    }

    public String getDisputeStatus() {
        return disputeStatus;
    }

    public void setDisputeStatus(String disputeStatus) {
        this.disputeStatus = disputeStatus;
    }

    public String getDispute() {
        return dispute;
    }

    public void setDispute(String dispute) {
        this.dispute = dispute;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
