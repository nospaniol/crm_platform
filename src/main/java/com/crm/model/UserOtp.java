package com.crm.model;

import java.io.Serializable;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "USEROTP")
public class UserOtp extends GenericInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "user_otp_sequence";

    @Id
    private Long otpId;
    private String otp;
    private User user;

    public Long getOtpId() {
        return otpId;
    }

    public void setOtpId(Long otpId) {
        this.otpId = otpId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserOtp{" + "otpId=" + otpId + ", otp=" + otp + '}';
    }

}
