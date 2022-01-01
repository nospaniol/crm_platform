package com.crm.model;

import java.io.Serializable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class DepartmentForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long clientProfileId;
    private Long departmentId;
    private String departmentName;
    private MultipartFile departmentLogo;

    public Long getClientProfileId() {
        return clientProfileId;
    }

    public void setClientProfileId(Long clientProfileId) {
        this.clientProfileId = clientProfileId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public MultipartFile getDepartmentLogo() {
        return departmentLogo;
    }

    public void setDepartmentLogo(MultipartFile departmentLogo) {
        this.departmentLogo = departmentLogo;
    }

}
