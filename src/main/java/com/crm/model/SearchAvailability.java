package com.crm.model;

import java.io.Serializable;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class SearchAvailability implements Serializable {

    private static final long serialVersionUID = 1L;

    private String searchItem;
    private String slotName;
    private Date searchDate;

    public String getSearchItem() {
        return searchItem;
    }

    public void setSearchItem(String searchItem) {
        this.searchItem = searchItem;
    }

    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public Date getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(Date searchDate) {
        this.searchDate = searchDate;
    }

    @Override
    public String toString() {
        return "SearchAvailability{" + "searchItem=" + searchItem + ", slotName=" + slotName + ", searchDate=" + searchDate + '}';
    }

}
