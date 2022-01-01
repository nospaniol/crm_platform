package com.crm.model;

import java.io.Serializable;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Data
public class InvoiceForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long invoiceId;
    private String client;
    private String department;
    private String feeType;
    private String invoiceDate;
    private String paidDate;
    private String invoiceAmount;
    private String tollAmount;
    private String feeAmount;
    private String totalPaid;
    private MultipartFile excelFile;
    private MultipartFile pdfFile;
    private String invoiceStat;

}
