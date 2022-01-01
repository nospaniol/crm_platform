package com.crm.model;

import java.io.Serializable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class VehicleUpload extends GenericInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String client;
    private String department;
    private String type;
    private MultipartFile vehicles;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MultipartFile getVehicles() {
        return vehicles;
    }

    public void setVehicles(MultipartFile vehicles) {
        this.vehicles = vehicles;
    }

}
