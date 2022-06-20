package com.group11.client.enums;

public enum PropertyType {
    START(1),
    JAIL(2),
    GO_JAIL(3),
    TAX(4),
    FERRY(5),
    PROPERTY(6);

    private final int propertyTypeId;

    PropertyType(final int propertyTypeId) {
        this.propertyTypeId = propertyTypeId;
    }

    public int getPropertyTypeId() {
        return propertyTypeId;
    }
}
