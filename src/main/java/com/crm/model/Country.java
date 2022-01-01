package com.crm.model;

import java.io.Serializable;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "countries")
public class Country extends GenericInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "countries_sequence";
    // @OneToMany(mappedBy = "country")
    //private Set<County> county;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "countryId", updatable = false, nullable = false)
    private Long countryId;
    //@Column(name = "country")
    private String countryName;

//    public Set<County> getCounty() {
//        return county;
//    }
//
//    public void setCounty(Set<County> county) {
//        this.county = county;
//    }
    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString() {
        return "Country{" + "countryId=" + countryId + ", countryName=" + countryName + '}';
    }

}
