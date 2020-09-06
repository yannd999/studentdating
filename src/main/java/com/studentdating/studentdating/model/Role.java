package com.studentdating.studentdating.model;

public enum Role {
    ADMIN("administrator"), LID("lid");

    private String description;

    Role(String description) {
        this.description = description;
    }

    Role() {
    }

    public String getDescription() {
        return description;
    }
}
