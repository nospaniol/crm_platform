package com.crm.model;

import java.io.Serializable;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "regions")
public class Region extends GenericInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "regions_sequence";
    //@ManyToOne
    //@JoinColumn(name = "countyId", nullable = false)
    private County county;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "regionId", updatable = false, nullable = false)
    private Long regionId;
    //@Column(name = "regionName")
    private String regionName;
    //@Transient
    private String countyName;
    //@Transient
    private String countryName;

    public County getCounty() {
        return county;
    }

    public void setCounty(County county) {
        this.county = county;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString() {
        return "Region{" + "county=" + county + ", regionId=" + regionId + ", regionName=" + regionName + ", countyName=" + countyName + ", countryName=" + countryName + '}';
    }

}
