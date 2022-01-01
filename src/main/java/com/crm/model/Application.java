package com.crm.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "applications")
@Data
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "applicant_sequence";

    @Id
    private Long applicationId;
    private String companyName;
    private String companyTitle;
    private String emailAddress;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    protected Date creationDate;
    protected Date updatedDate;
    private String creationTime;
    private String updatedTime;
    private boolean status;

}
