package com.crm.model;

import java.io.Serializable;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tagTypes")
public class TagType extends GenericInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "tagType_sequence";

    @Id
    private Long tagTypeId;
    private String tagTypeName;

    public Long getTagTypeId() {
        return tagTypeId;
    }

    public void setTagTypeId(Long tagTypeId) {
        this.tagTypeId = tagTypeId;
    }

    public String getTagTypeName() {
        return tagTypeName;
    }

    public void setTagTypeName(String tagTypeName) {
        this.tagTypeName = tagTypeName;
    }

    public String toString() {
        return "{\"tagTypeId\":" + this.tagTypeId + ", \"tagTypeName\":\"" + this.tagTypeName + "\"}";
    }

}
