package com.crm.model;

import java.io.Serializable;
import java.util.List;

public class SpendingResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    String title;
    String message;
    String pageTitle;
    String tableTitle;
    String total;
    List<?> results;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getTableTitle() {
        return tableTitle;
    }

    public void setTableTitle(String tableTitle) {
        this.tableTitle = tableTitle;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<?> getResults() {
        return results;
    }

    public void setResults(List<?> results) {
        this.results = results;
    }

}
