package com.crm.model;

import java.io.Serializable;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Data
public class TransactionUpload implements Serializable {

    private static final long serialVersionUID = 1L;

    private String client;
    private String department;
    private String type;
    private String dateType;
    private String transactionDate;
    private MultipartFile transactions;

}
