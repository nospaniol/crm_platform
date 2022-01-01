package com.crm.model;

import java.io.Serializable;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "company_info")
public class Company extends GenericInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "company_info_sequence";
    //private static final int serialVersionUID = 1L;

    @Id
    //@Column(name = "companyId")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;
    //@Column(name = "company_name")
    private String companyName;
    //@Column(name = "company_motto")
    private String companyMotto;
    //@Column(name = "company_logo")
    private String companyLogo;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyMotto() {
        return companyMotto;
    }

    public void setCompanyMotto(String companyMotto) {
        this.companyMotto = companyMotto;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    @Override
    public String toString() {
        return "Company{" + "companyId=" + companyId + ", companyName=" + companyName + ", companyMotto=" + companyMotto + ", companyLogo=" + companyLogo + '}';
    }

}
