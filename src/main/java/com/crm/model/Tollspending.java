package com.crm.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class Tollspending implements Serializable {

    private static final long serialVersionUID = 1L;

    private ClientProfile clientProfile;
    private Department department;
    private Vehicle vehicle;
    private String title;
    private Double amount;

    private String getPlate() {
        return this.vehicle.getLicensePlate();
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
