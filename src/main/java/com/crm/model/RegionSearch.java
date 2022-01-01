package com.crm.model;

import java.io.Serializable;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class RegionSearch implements Serializable {

    private static final long serialVersionUID = 1L;

    private String country;

    private String county;

    private String region;

    private String speciality;

    private Date crmDate;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public Date getcrmDate() {
        return crmDate;
    }

    public void setcrmDate(Date crmDate) {
        this.crmDate = crmDate;
    }

    @Override
    public String toString() {
        return "RegionSearch{" + "country=" + country + ", county=" + county + ", region=" + region + ", speciality=" + speciality + ", crmDate=" + crmDate + '}';
    }

}
