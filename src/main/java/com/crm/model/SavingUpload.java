package com.crm.model;

import java.io.Serializable;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class SavingUpload implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long savingId;
    private String client;
    private String department;
    private Double amount;
    private Double balance;

}
