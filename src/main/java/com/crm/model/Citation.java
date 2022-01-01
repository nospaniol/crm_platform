package com.crm.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "citations")
@Data
public class Citation implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "citation_sequence";
    @DBRef
    private ClientProfile clientProfile;
    @DBRef
    private Department department;
    @DBRef
    private Vehicle vehicle;
    @DBRef
    private State licensePlateState;
    @DBRef
    private State violationState;
    @DBRef
    private CitationType citationType;

    @Id
    private Long citationId;
    private String violationNumber;
    private String citationStatus;
    private Date citationDate;
    private String citationMonth;
    private int citationYear;
    private Double citationAmount;
    private Double feeAmount;
    private Double paidAmount;
    private String payableTo;
    private Date paidDate;
    protected Date creationDate;
    protected Date updatedDate;
    private String creationTime;
    private String updatedTime;
    private boolean status;
    private boolean citationDispute;

}
