package com.crm.enums;

public enum Role {

    ADMIN("ADMIN"),
    STAFF("Staff"),
    CLIENT("client");

    private String description;

    Role(String description) {
        this.description = description;
    }

}
