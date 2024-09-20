package com.sakovolga.bookstore.entity.enums;

public enum Role {
    CUSTOMER,
    MANAGER,
    ADMIN;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
