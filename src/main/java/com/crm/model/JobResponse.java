package com.crm.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class JobResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    String title;
    String message;
    String role;
    Double total;
    List<?> results;
    Page<?> result;

}
