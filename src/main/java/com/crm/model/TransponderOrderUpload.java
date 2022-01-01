package com.crm.model;

import java.io.Serializable;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Data
public class TransponderOrderUpload implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long orderId;
    private int transponderQuantity;
    private int extraVelcro;
    private String clientProfile;
    private String department;
    private String assetName;
    private String domicileTerminal;
    private String licensePlate;
    private String customerVehicleId;
    private String state;
    private String instructions;
    private String shippingAddress;
    private MultipartFile assetFile;
    private MultipartFile shippingFile;

}
