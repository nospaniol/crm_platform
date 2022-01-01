package com.crm.model;

import java.io.Serializable;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 *
 * @author nospaniol
 */
@Component
@Data
public class Spend implements Serializable {

    private static final long serialVersionUID = 1L;

    private Vehicle vehicle;
    private Double total;

}
