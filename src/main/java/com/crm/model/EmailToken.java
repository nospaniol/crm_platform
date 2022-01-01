package com.crm.model;

import java.io.Serializable;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "email_token")
public class EmailToken implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "email_token_sequence";

    @Id
    public Long emailTokenId;
    private String emailAddress;
    private String emailOtp;
    private boolean status;

    public Long getEmailTokenId() {
        return emailTokenId;
    }

    public void setEmailTokenId(Long emailTokenId) {
        this.emailTokenId = emailTokenId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailOtp() {
        return emailOtp;
    }

    public void setEmailOtp(String emailOtp) {
        this.emailOtp = emailOtp;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "EmailToken{" + "emailTokenId=" + emailTokenId + ", emailAddress=" + emailAddress + ", emailOtp=" + emailOtp + '}';
    }

}
