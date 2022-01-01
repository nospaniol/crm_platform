package com.crm.model;

import java.io.Serializable;
import org.springframework.stereotype.Component;

@Component
public class Search implements Serializable {

    private static final long serialVersionUID = 1L;

    private String searchItem;
    private String additionalField;
    private String searchDate;

    public String getSearchItem() {
        return searchItem;
    }

    public void setSearchItem(String searchItem) {
        this.searchItem = searchItem;
    }

    public String getAdditionalField() {
        return additionalField;
    }

    public void setAdditionalField(String additionalField) {
        this.additionalField = additionalField;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }

    @Override
    public String toString() {
        return "Search{" + "searchItem=" + searchItem + ", additionalField=" + additionalField + ", searchDate=" + searchDate + '}';
    }

}
