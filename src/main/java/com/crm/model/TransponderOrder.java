package com.crm.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transponder_orders")
@Data
public class TransponderOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "transponder_order_sequence";

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
    protected Date creationDate;
    protected Date updatedDate;
    private String creationTime;
    private String updatedTime;
    private boolean status;

    private String getFormattedDate(Date todate) {
        if (todate == null) {
            todate = new Date();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = formatter.format(todate);
        return strDate;
    }

}
