package com.crm.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "citation_dispute")
@Data
public class CitationDispute implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "citation_dispute_sequence";

    @DBRef
    private ClientProfile clientProfile;
    @DBRef
    private Department department;
    @DBRef
    private Citation citation;

    @Id
    private Long disputeId;
    private String disputeStatus;
    private String dispute;
    protected Date creationDate;
    protected Date updatedDate;
    private String creationTime;
    private String updatedTime;
    private boolean status;
    private boolean transactionDispute;

}
