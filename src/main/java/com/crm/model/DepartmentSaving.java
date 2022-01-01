package com.crm.model;

import dev.morphia.annotations.Property;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "department_savings")
@Data
public class DepartmentSaving implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "department_saving_sequence";

    @DBRef
    private ClientProfile clientProfile;
    @DBRef
    private Department department;

    @Id
    private Long savingId;
    @Property("price")
    private Double amount;
    protected Date creationDate;
    protected Date updatedDate;
    private String creationTime;
    private String updatedTime;
    private boolean status;

}
