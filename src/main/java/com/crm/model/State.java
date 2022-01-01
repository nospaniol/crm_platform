package com.crm.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "states")
@Data
public class State implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "state_sequence";

    @Id
    private Long stateId;
    private String stateName;
    protected Date creationDate;
    protected Date updatedDate;
    private String creationTime;
    private String updatedTime;
    private boolean status;

}
