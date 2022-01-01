package com.crm.generic;

import com.crm.model.Vehicle;

public class Report {

    private Vehicle vehicle;
    private String licensePlate;
    private String exitLane;
    private double amount;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getExitLane() {
        return exitLane;
    }

    public void setExitLane(String exitLane) {
        this.exitLane = exitLane;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
