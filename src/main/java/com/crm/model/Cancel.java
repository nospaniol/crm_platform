package com.crm.model;

import java.io.Serializable;
import org.springframework.stereotype.Component;

@Component
public class Cancel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long cancelItem;
    private Long clientId;

    public Long getCancelItem() {
        return cancelItem;
    }

    public void setCancelItem(Long cancelItem) {
        this.cancelItem = cancelItem;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

}
