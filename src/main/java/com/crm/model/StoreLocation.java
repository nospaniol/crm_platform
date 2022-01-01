package com.crm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "store_locations")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreLocation extends GenericInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SEQUENCE_NAME = "storelocation_sequence";

    @Id
    private Long storeLocationId;
    private String storeLocationName;

    public Long getStoreLocationId() {
        return storeLocationId;
    }

    public void setStoreLocationId(Long storeLocationId) {
        this.storeLocationId = storeLocationId;
    }

    public String getStoreLocationName() {
        return storeLocationName;
    }

    public void setStoreLocationName(String storeLocationName) {
        this.storeLocationName = storeLocationName;
    }

    @Override
    public String toString() {
        return "{\"storeLocationId\":" + storeLocationId + ", \"storeLocationName\":\"" + storeLocationName + "\"}";
    }
}
