package com.crm.model;

import java.io.Serializable;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class VehicleForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long vehicleId;
    private String licensePlate;
    private String model;
    private String color;
    private String make;
    private String axle;
    private String tagType;
    private String startDate;
    private String endDate;
    private String state;
    private String year;
    private String unit;
    private String client;
    private String department;
    private String tollTagId;
    private String vin;
    private String type;
    private String vehicleStatus;
    private String vehicleComment;

}
