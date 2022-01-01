package com.crm.model;

import java.io.Serializable;
import org.springframework.stereotype.Component;

@Component
public class MissingInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String info;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
