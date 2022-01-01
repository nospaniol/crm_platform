package com.crm.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vehicle_files")
@Data
public class VehicleFile implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "vehicle_file_sequence";

    @DBRef
    private ClientProfile clientProfile;
    @DBRef
    private Department department;

    @Id
    private Long vehicleFileId;
    private Date vehicleDate;
    private String vehicleName;
    private Binary vehicles;
    protected Date creationDate;
    protected Date updatedDate;
    private String creationTime;
    private String updatedTime;
    private boolean status;

    @Override
    public String toString() {
        return "VehicleFile{" + "clientProfile=" + clientProfile + ", department=" + department + ", vehicleFileId=" + vehicleFileId + ", vehicleDate=" + vehicleDate + ", vehicleName=" + vehicleName + ", creationDate=" + creationDate + ", updatedDate=" + updatedDate + ", creationTime=" + creationTime + ", updatedTime=" + updatedTime + ", status=" + status + '}';
    }

    public Long getVehicleFileId() {
        return vehicleFileId;
    }

    public void setVehicleFileId(Long vehicleFileId) {
        this.vehicleFileId = vehicleFileId;
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

    public Date getVehicleDate() {
        return vehicleDate;
    }

    public void setVehicleDate(Date vehicleDate) {
        this.vehicleDate = vehicleDate;
    }

    public Binary getVehicles() {
        return vehicles;
    }

    public void setVehicles(Binary vehicles) {
        this.vehicles = vehicles;
    }

}
