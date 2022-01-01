package com.crm.model;

import java.io.Serializable;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ClientUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long clientProfileId;
    private Long departmentId;
    private Long userId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String designation;
    private String password;
    private String rpassword;
    private String emailAddress;
    private String phoneNumber;

}
