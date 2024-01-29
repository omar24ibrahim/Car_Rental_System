package com.carrental.carrental.model.enums;

public enum BillingStatus {
    PAID("PAID"),
    PENDING("PENDING");

    private final String displayName;

    BillingStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
