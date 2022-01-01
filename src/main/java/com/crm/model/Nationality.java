package com.crm.model;

import java.io.Serializable;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Nationality")
public class Nationality extends GenericInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    //private static final int serialVersionUID = 2902003646790602767L;
    public static final String SEQUENCE_NAME = "nationality_sequence";
    @Id
    //@Column(name = "nationalityId")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nationalityId;
    //@Column(name = "nation")
    private String nation;
    //@Column(name = "code")
    private String code;
    //@Column(name = "nationality")
    private String nationality;

    public Long getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(Long nationalityId) {
        this.nationalityId = nationalityId;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public String toString() {
        return "Nationality{" + "nationalityId=" + nationalityId + ", nation=" + nation + ", code=" + code + ", nationality=" + nationality + '}';
    }

}
