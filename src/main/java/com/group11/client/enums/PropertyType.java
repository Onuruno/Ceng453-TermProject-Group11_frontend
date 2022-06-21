package com.group11.client.enums;

public enum PropertyType {
    START("Start"),
    JAIL("Jail"),
    GO_JAIL("GoJail"),
    TAX("Tax"),
    FERRY("Ferry"),
    PROPERTY("Property");

    private final String propertyTypeId;

    PropertyType(final String propertyTypeId) {
        this.propertyTypeId = propertyTypeId;
    }

    public String getPropertyTypeId() {
        return propertyTypeId;
    }
}
