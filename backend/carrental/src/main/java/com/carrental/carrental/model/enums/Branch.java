package com.carrental.carrental.model.enums;

public enum Branch {
    AIRPORT("AIRPORT"),
    DOWNTOWN("DOWNTOWN");

    private final String displayName;

    Branch(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
