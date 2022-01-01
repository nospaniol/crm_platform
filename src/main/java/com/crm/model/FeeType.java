package com.crm.model;

import java.io.Serializable;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "feeTypes")
public class FeeType extends GenericInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "feeType_sequence";

    @Id
    private Long feeTypeId;
    private String feeTypeName;

    public Long getFeeTypeId() {
        return feeTypeId;
    }

    public void setFeeTypeId(Long feeTypeId) {
        this.feeTypeId = feeTypeId;
    }

    public String getFeeTypeName() {
        return feeTypeName;
    }

    public void setFeeTypeName(String feeTypeName) {
        this.feeTypeName = feeTypeName;
    }

}
