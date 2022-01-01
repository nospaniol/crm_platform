package com.crm.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vehicles")
@Data
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "vehicle_sequence";

    @DBRef
    private ClientProfile clientProfile;

    @DBRef
    private Department department;

    @DBRef
    private Axle axle;
    @DBRef
    private TagType tagType;
    @DBRef
    private StoreLocation storeLocation;
    @DBRef
    private VehicleFile vehicleFile;
    @Id
    private Long vehicleId;
    private String licensePlate;
    private String model;
    private String color;
    private String make;
    private Date startDate;
    private Date endDate;
    private String store;
    private String state;
    private String year;
    private String unit;
    private String tollTagId;
    private String vin;
    private String type;
    private String vehicleStatus;
    private String vehicleComment;
    protected Date creationDate;
    protected Date updatedDate;
    private String creationTime;
    private String updatedTime;
    private boolean status;

    @Override
    public String toString() {
        return "{ \"axle\":" + axle + ", \"tagType\":" + tagType + ", \"storeLocation\":" + storeLocation + ",\"vehicleId\":" + vehicleId + ",\"licensePlate\":\"" + licensePlate + "\", \"model\":\"" + model + "\", \"color\":\"" + color + "\", \"make\":\"" + make + "\", \"startDate\":\"" + getFormattedDate(startDate) + "\", \"endDate\":\"" + getFormattedDate(endDate) + "\", \"store\":\"" + store + "\", \"state\":\"" + state + "\", \"year\":\"" + year + "\", \"unit\":\"" + unit + "\", \"tollTagId\":\"" + tollTagId + "\", \"vin\":\"" + vin + "\", \"type\":\"" + type + "\", \"vehicleStatus\":\"" + vehicleStatus + "\", \"vehicleComment\":\"" + vehicleComment + "\", \"creationDate\":\"" + getFormattedDate(creationDate) + "\", \"updatedDate\":\"" + getFormattedDate(updatedDate) + "\", \"creationTime\":\"" + creationTime + "\", \"updatedTime\":\"" + updatedTime + "\", \"status\":\"" + status + "\"}";
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

    public Axle getAxle() {
        return axle;
    }

    public void setAxle(Axle axle) {
        this.axle = axle;
    }

    public TagType getTagType() {
        return tagType;
    }

    public void setTagType(TagType tagType) {
        this.tagType = tagType;
    }

    public StoreLocation getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(StoreLocation storeLocation) {
        this.storeLocation = storeLocation;
    }

    public VehicleFile getVehicleFile() {
        return vehicleFile;
    }

    public void setVehicleFile(VehicleFile vehicleFile) {
        this.vehicleFile = vehicleFile;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTollTagId() {
        return tollTagId;
    }

    public void setTollTagId(String tollTagId) {
        this.tollTagId = tollTagId;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(String vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public String getVehicleComment() {
        return vehicleComment;
    }

    public void setVehicleComment(String vehicleComment) {
        this.vehicleComment = vehicleComment;
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
