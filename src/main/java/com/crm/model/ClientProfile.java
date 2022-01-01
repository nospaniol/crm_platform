package com.crm.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "client_profile")
@Data
public class ClientProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "client_profile_sequence";

    @Id
    private Long clientProfileId;
    private String companyName;
    private String companyEmailAddress;
    private String companyPhoneNumber;
    private String postalAddress;
    private String category;
    private String accountType;
    private Binary companyLogo;
    protected Date creationDate;
    protected Date updatedDate;
    private String creationTime;
    private String updatedTime;
    private boolean status;

    @Override
    public String toString() {
        return "{\"clientProfileId\":\"" + clientProfileId + "\", \"companyName\":\"" + companyName + "\", \"companyEmailAddress\":\"" + companyEmailAddress + "\", \"companyPhoneNumber\":\"" + companyPhoneNumber + "\", \"postalAddress\":\"" + postalAddress + "\", \"category\":\"" + category + "\", \"accountType\":\"" + accountType + "\", \"creationDate\":\"" + getFormattedDate(creationDate) + "\", \"updatedDate\":\"" + getFormattedDate(updatedDate) + "\", \"creationTime\":\"" + creationTime + "\", \"updatedTime\":\"" + updatedTime + "\", \"status\":\"" + status + "\"}";
    }

    private String getFormattedDate(Date todate) {
        if (todate == null) {
            todate = new Date();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(todate);
        return strDate;
    }

    public Long getClientProfileId() {
        return clientProfileId;
    }

    public void setClientProfileId(Long clientProfileId) {
        this.clientProfileId = clientProfileId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyEmailAddress() {
        return companyEmailAddress;
    }

    public void setCompanyEmailAddress(String companyEmailAddress) {
        this.companyEmailAddress = companyEmailAddress;
    }

    public String getCompanyPhoneNumber() {
        return companyPhoneNumber;
    }

    public void setCompanyPhoneNumber(String companyPhoneNumber) {
        this.companyPhoneNumber = companyPhoneNumber;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Binary getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(Binary companyLogo) {
        this.companyLogo = companyLogo;
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
