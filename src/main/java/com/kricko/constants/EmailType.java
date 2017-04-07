package com.kricko.constants;

public enum EmailType {
    ACCOUNTS("accounts"),
    BUSINESS("business"),
    ORDERS("orders"),
    PUBLICATION("publication"),
    USER("user"),
    PHOTOSHOOT("photoshoot");

    private final String value;

    EmailType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
