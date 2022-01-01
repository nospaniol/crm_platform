package com.crm.model;

import java.io.Serializable;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "authorities")
public class Authority implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "authority_sequence";

    @Id
    private Long authorityId;
    private String role;
    private boolean status;

    public Long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Authority() {
    }

    public Authority(String role, boolean status) {
        this.role = role;
        this.status = status;
    }

}
