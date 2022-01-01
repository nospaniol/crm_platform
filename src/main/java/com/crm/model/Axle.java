package com.crm.model;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "axle")
public class Axle extends GenericInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "axle_sequence";

    @Id
    private Long axleId;
    private String axleName;

    public Long getAxleId() {
        return axleId;
    }

    public void setAxleId(Long axleId) {
        this.axleId = axleId;
    }

    public String getAxleName() {
        return axleName;
    }

    public void setAxleName(String axleName) {
        this.axleName = axleName;
    }

    @Override
    public String toString() {
        return "{\"axleId\":" + axleId + ", \"axleName\":\"" + axleName + "\"}";
    }

}
