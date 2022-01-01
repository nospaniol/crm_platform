package com.crm.model;

import java.io.Serializable;
import org.springframework.stereotype.Component;

@Component
public class CitationForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String client;
    private String department;
    private String licensePlate;
    private String licensePlateState;
    private String violationState;
    private Long citationId;
    private String violationNumber;
    private String citationType;
    private String citationStatus;
    private String citationDate;
    private String citationAmount;
    private String feeAmount;
    private String paidAmount;
    private String payableTo;
    private String paidDate;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getLicensePlateState() {
        return licensePlateState;
    }

    public void setLicensePlateState(String licensePlateState) {
        this.licensePlateState = licensePlateState;
    }

    public String getViolationState() {
        return violationState;
    }

    public void setViolationState(String violationState) {
        this.violationState = violationState;
    }

    public Long getCitationId() {
        return citationId;
    }

    public void setCitationId(Long citationId) {
        this.citationId = citationId;
    }

    public String getViolationNumber() {
        return violationNumber;
    }

    public void setViolationNumber(String violationNumber) {
        this.violationNumber = violationNumber;
    }

    public String getCitationType() {
        return citationType;
    }

    public void setCitationType(String citationType) {
        this.citationType = citationType;
    }

    public String getCitationStatus() {
        return citationStatus;
    }

    public void setCitationStatus(String citationStatus) {
        this.citationStatus = citationStatus;
    }

    public String getCitationDate() {
        return citationDate;
    }

    public void setCitationDate(String citationDate) {
        this.citationDate = citationDate;
    }

    public String getCitationAmount() {
        return citationAmount;
    }

    public void setCitationAmount(String citationAmount) {
        this.citationAmount = citationAmount;
    }

    public String getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getPayableTo() {
        return payableTo;
    }

    public void setPayableTo(String payableTo) {
        this.payableTo = payableTo;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

}
