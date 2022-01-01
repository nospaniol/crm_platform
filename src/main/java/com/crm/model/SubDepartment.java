package com.crm.model;

import java.io.Serializable;
import java.util.Base64;
import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "client_sub_departments")
@Data
public class SubDepartment extends GenericInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "sub_department_sequence";
    @DBRef
    private Department department;

    @Id
    private Long departmentId;
    private String departmentName;
    private Binary departmentLogo;

    public String getLogo() {
        return Base64.getEncoder().encodeToString(this.departmentLogo.getData());
    }

}
