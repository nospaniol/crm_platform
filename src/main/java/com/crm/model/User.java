package com.crm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "user_sequence";

    @DBRef
    private Authority authority;
    @DBRef
    private EmailToken emailToken;
    @DBRef
    private PhoneToken phoneToken;
    @DBRef
    private ClientProfile clientProfile;
    @DBRef
    private Department department;
    @Id
    private Long userId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String designation;
    private String password;
    private boolean status;
    private boolean groupStatus;
    private boolean departmentStatus;
    private boolean administrativeStatus;

    @Transient
    private String emailAddress;
    @Transient
    private String phoneNumber;

//    @DBRef
//    private Collection<Role> roles;
    public ClientProfile getClientProfile() {
        return clientProfile;
    }

    public void setClientProfile(ClientProfile clientProfile) {
        this.clientProfile = clientProfile;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public EmailToken getEmailToken() {
        return emailToken;
    }

    public void setEmailToken(EmailToken emailToken) {
        this.emailToken = emailToken;
    }

    public PhoneToken getPhoneToken() {
        return phoneToken;
    }

    public void setPhoneToken(PhoneToken phoneToken) {
        this.phoneToken = phoneToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.emailToken.getEmailAddress();
    }

    public String getPhone() {
        return this.phoneToken.getPhoneNumber();
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public boolean isGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(boolean groupStatus) {
        this.groupStatus = groupStatus;
    }

    public boolean isDepartmentStatus() {
        return departmentStatus;
    }

    public void setDepartmentStatus(boolean departmentStatus) {
        this.departmentStatus = departmentStatus;
    }

    public boolean isAdministrativeStatus() {
        return administrativeStatus;
    }

    public void setAdministrativeStatus(boolean administrativeStatus) {
        this.administrativeStatus = administrativeStatus;
    }

//    public Collection<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Collection<Role> roles) {
//        this.roles = roles;
//    }
    public User(Authority authority, EmailToken emailToken, PhoneToken phoneToken, String firstName, String lastName, String middleName, String password, String emailAddress, String phoneNumber, boolean status) {
        this.authority = authority;
        this.emailToken = emailToken;
        this.phoneToken = phoneToken;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.password = password;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public User() {
    }

}
