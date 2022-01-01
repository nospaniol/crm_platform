package com.crm.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

public class LoginResponse implements Serializable {

    String title;
    String message;
    String role;
    String phone;
    String email;
    String userId;
    String firstName;
    String middleName;
    String lastName;
    String loggedUser;
    String clientId;
    String parentClientId;
    String departmentMode;
    DashboardResponse dashboardResponse;
    ClientProfile clientProfile;
    Department department;
    List<Department> departments;

    @Override
    public String toString() {
        return "{\"title\":\"" + title + "\", \"message\":\"" + message + "\", \"role\":\"" + role + "\", \"phone\":\"" + phone + "\", \"email\":\"" + email + "\", \"userId\":\"" + userId + "\", \"firstName\":\"" + firstName + "\", \"middleName\":\"" + middleName + "\", \"lastName\":\"" + lastName + "\", \"loggedUser\":\"" + loggedUser + "\", \"clientId\":\"" + clientId + "\", \"parentClientId\":\"" + parentClientId + "\", \"departmentMode\":\"" + departmentMode + "\", \"clientProfile\":" + clientProfile + ", \"department\":" + department + ", \"departments\":" + departments + ", \"dashboardResponse\":" + dashboardResponse + "}";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(String loggedUser) {
        this.loggedUser = loggedUser;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getParentClientId() {
        return parentClientId;
    }

    public void setParentClientId(String parentClientId) {
        this.parentClientId = parentClientId;
    }

    public String getDepartmentMode() {
        return departmentMode;
    }

    public void setDepartmentMode(String departmentMode) {
        this.departmentMode = departmentMode;
    }

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

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public DashboardResponse getDashboardResponse() {
        return dashboardResponse;
    }

    public void setDashboardResponse(DashboardResponse dashboardResponse) {
        this.dashboardResponse = dashboardResponse;
    }
}
