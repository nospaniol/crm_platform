package com.crm.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "order_shipping_files")
@Data
public class OrderShippingFile implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "order_shipping_file_sequence";

    @DBRef
    private ClientProfile clientProfile;
    @DBRef
    private Department department;

    @Id
    private Long orderFileId;
    private Date orderDate;
    private String orderName;
    private Binary orders;
    protected Date creationDate;
    protected Date updatedDate;
    private String creationTime;
    private String updatedTime;
    private boolean status;

    @Override
    public String toString() {
        return "OrderFile{" + "clientProfile=" + clientProfile + ", department=" + department + ", orderFileId=" + orderFileId + ", orderDate=" + orderDate + ", orderName=" + orderName + ", creationDate=" + creationDate + ", updatedDate=" + updatedDate + ", creationTime=" + creationTime + ", updatedTime=" + updatedTime + ", status=" + status + '}';
    }

    public Long getOrderFileId() {
        return orderFileId;
    }

    public void setOrderFileId(Long orderFileId) {
        this.orderFileId = orderFileId;
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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Binary getOrders() {
        return orders;
    }

    public void setOrders(Binary orders) {
        this.orders = orders;
    }

}
