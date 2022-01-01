package com.crm.model;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class ChartResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    String title;
    String message;
    Double total;
    List<?> agencies;
    List<?> monthly;
    List<?> dailies;

}
