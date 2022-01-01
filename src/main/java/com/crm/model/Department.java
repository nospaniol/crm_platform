package com.crm.model;

import java.io.Serializable;
import java.util.Base64;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "client_departments")
public class Department extends GenericInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "department_sequence";
    @DBRef
    private ClientProfile clientProfile;

    @Id
    private Long departmentId;
    private String departmentName;
    private Binary departmentLogo;

    public ClientProfile getClientProfile() {
        return clientProfile;
    }

    public void setClientProfile(ClientProfile clientProfile) {
        this.clientProfile = clientProfile;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Binary getDepartmentLogo() {
        return departmentLogo;
    }

    public void setDepartmentLogo(Binary departmentLogo) {
        this.departmentLogo = departmentLogo;
    }

    public String getLogo() {
        return Base64.getEncoder().encodeToString(this.departmentLogo.getData());
    }

}
