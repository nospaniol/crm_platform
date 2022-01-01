package com.crm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.morphia.annotations.Property;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transactions")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    // private static final long serialVersionUID = 123L;
    public static final String SEQUENCE_NAME = "transaction_sequence";

    @DBRef
    private ClientProfile clientProfile;
    @DBRef
    private Department department;
    @DBRef
    private Vehicle vehicle;
    @Id
    private Long transactionId;
    private Long transactionFileId;
    private Date transactionDate;
    private Date postedDate;
    private String state;
    private String agency;
    private String client;
    private String transactionClass;
    private String transactionToll;
    private Date exitDateTime;
    private Date postedDateTime;
    private String exitLane;
    private String exitLocation;
    private String transactionMonth;
    private int transactionYear;
    private String postedMonth;
    private int postedYear;
    @Property("price")
    private Double amount;
    protected Date creationDate;
    protected Date updatedDate;
    private String creationTime;
    private String updatedTime;
    private boolean status;
    private boolean transactionDispute;
    private String uploadType;
    @Transient
    private String licensePlate;
    @Transient
    private String plate;
    @Transient
    private boolean empty;

    public String getLicensePlate() {
        if (vehicle != null) {
            return this.vehicle.getLicensePlate();
        }
        return "";
    }

    @Override
    public String toString() {
        return "{\"vehicle\":" + vehicle + ", \"state\":" + state + ", \"agency\":" + agency + ", \"exitDateTime\":\"" + getFormattedDate(exitDateTime) + "\",  \"exitLocation\":\"" + exitLocation + "\", \"exitLane\":\"" + exitLane + "\", \"amount\":\"" + amount + "\"}";
    }

    private String getFormattedDate(Date todate) {
        if (todate == null) {
            todate = new Date();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(todate);
        return strDate;
    }

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

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getTransactionFileId() {
        return transactionFileId;
    }

    public void setTransactionFileId(Long transactionFileId) {
        this.transactionFileId = transactionFileId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getTransactionClass() {
        return transactionClass;
    }

    public void setTransactionClass(String transactionClass) {
        this.transactionClass = transactionClass;
    }

    public String getTransactionToll() {
        return transactionToll;
    }

    public void setTransactionToll(String transactionToll) {
        this.transactionToll = transactionToll;
    }

    public Date getExitDateTime() {
        return exitDateTime;
    }

    public void setExitDateTime(Date exitDateTime) {
        this.exitDateTime = exitDateTime;
    }

    public Date getPostedDateTime() {
        return postedDateTime;
    }

    public void setPostedDateTime(Date postedDateTime) {
        this.postedDateTime = postedDateTime;
    }

    public String getExitLane() {
        return exitLane;
    }

    public void setExitLane(String exitLane) {
        this.exitLane = exitLane;
    }

    public String getExitLocation() {
        return exitLocation;
    }

    public void setExitLocation(String exitLocation) {
        this.exitLocation = exitLocation;
    }

    public String getTransactionMonth() {
        return transactionMonth;
    }

    public void setTransactionMonth(String transactionMonth) {
        this.transactionMonth = transactionMonth;
    }

    public int getTransactionYear() {
        return transactionYear;
    }

    public void setTransactionYear(int transactionYear) {
        this.transactionYear = transactionYear;
    }

    public String getPostedMonth() {
        return postedMonth;
    }

    public void setPostedMonth(String postedMonth) {
        this.postedMonth = postedMonth;
    }

    public int getPostedYear() {
        return postedYear;
    }

    public void setPostedYear(int postedYear) {
        this.postedYear = postedYear;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    public boolean isTransactionDispute() {
        return transactionDispute;
    }

    public void setTransactionDispute(boolean transactionDispute) {
        this.transactionDispute = transactionDispute;
    }

    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
