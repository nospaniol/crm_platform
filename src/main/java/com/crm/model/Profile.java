package com.crm.model;

import java.io.Serializable;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Data
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long clientProfileId;
    private String companyName;
    private String companyEmailAddress;
    private String companyPhoneNumber;
    private String postalAddress;
    private String category;
    private String accountType;
    private MultipartFile companyLogo;

}
