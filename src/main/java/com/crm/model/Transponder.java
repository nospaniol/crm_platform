package com.crm.model;

import java.io.Serializable;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Component
@Data
@Document(collection = "saved_fullfillments")
public class Transponder implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String SEQUENCE_NAME = "transponder_sequence";
    @DBRef
    private ClientProfile clientProfile;

    @DBRef
    private Department department;

    @DBRef
    private OrderAssetFile orderAssetFile;

    @DBRef
    private OrderShippingFile orderShippingFile;
    @Id
    private Long orderId;
    private int transponderQuantity;
    private int extraVelcro;
    private String assetName;
    private String domicileTerminal;
    private String licensePlate;
    private String customerVehicleId;
    private String state;
    private String instructions;
    private String shippingAddress;
    private String orderStatus;

}
