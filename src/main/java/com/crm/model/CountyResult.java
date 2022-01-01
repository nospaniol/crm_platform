package com.crm.model;

import java.io.Serializable;
import org.springframework.stereotype.Component;

@Component
public class CountyResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private String countyName;

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    @Override
    public String toString() {
        return "CountyResult{" + "countyName=" + countyName + '}';
    }

}
