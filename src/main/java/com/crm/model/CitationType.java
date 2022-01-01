package com.crm.model;

import java.io.Serializable;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "citationTypes")
public class CitationType extends GenericInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "citationType_sequence";

    @Id
    private Long citationTypeId;
    private String citationTypeName;

    public Long getCitationTypeId() {
        return citationTypeId;
    }

    public void setCitationTypeId(Long citationTypeId) {
        this.citationTypeId = citationTypeId;
    }

    public String getCitationTypeName() {
        return citationTypeName;
    }

    public void setCitationTypeName(String citationTypeName) {
        this.citationTypeName = citationTypeName;
    }

}
