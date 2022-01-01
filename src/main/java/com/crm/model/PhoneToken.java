package com.crm.model;

import java.io.Serializable;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "phone_token")
public class PhoneToken implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "phone_token_sequence";

    @Id
    private Long phoneTokenId;
    private String phoneNumber;
    private String phoneOtp;
    private boolean status;

    public Long getPhoneTokenId() {
        return phoneTokenId;
    }

    public void setPhoneTokenId(Long phoneTokenId) {
        this.phoneTokenId = phoneTokenId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneOtp() {
        return phoneOtp;
    }

    public void setPhoneOtp(String phoneOtp) {
        this.phoneOtp = phoneOtp;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PhoneToken{" + "phoneTokenId=" + phoneTokenId + ", phoneNumber=" + phoneNumber + ", phoneOtp=" + phoneOtp + '}';
    }

}
