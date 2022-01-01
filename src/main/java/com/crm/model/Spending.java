package com.crm.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "spendings")
@Data
public class Spending implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "spending_sequence";

    @DBRef
    private ClientProfile clientProfile;
    @DBRef
    private Department department;
    @DBRef
    private Vehicle vehicle;

    @Id
    private Long spendingsId;
    private String spendingMonth;
    private int spendingYear;
    private Double amount;
    protected Date creationDate;
    protected Date updatedDate;
    private String creationTime;
    private String updatedTime;
    private boolean status;

}
