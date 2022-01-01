package com.crm.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transaction_files")
@Data
public class TransactionFile implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "transaction_file_sequence";

    @Id
    private Long transactionFileId;
    @DBRef
    private ClientProfile clientProfile;
    @DBRef
    private Department department;
    private Date transactionDate;
    private Binary transactions;
    private String transactionName;
    protected Date creationDate;
    protected Date updatedDate;
    private String creationTime;
    private String updatedTime;
    private boolean status;

}
