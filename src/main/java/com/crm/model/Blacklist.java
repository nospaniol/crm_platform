package com.crm.model;

import java.io.Serializable;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Blacklist")
public class Blacklist extends GenericInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "blacklist_sequence";
    /**
     *
     */
    //private static final int serialVersionUID = -3904146970682953800L;
    @Id
    //@Column(name = "blacklistId")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blacklistId;
    //@Column(name = "email")
    private String email;
    //@Column(name = "reason")
    private String reason;

    public Long getBlacklistId() {
        return blacklistId;
    }

    public void setBlacklistId(Long blacklistId) {
        this.blacklistId = blacklistId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Blacklist{" + "blacklistId=" + blacklistId + ", email=" + email + ", reason=" + reason + '}';
    }

}
