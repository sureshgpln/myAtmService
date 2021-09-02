package com.util;

public enum CurrencyTypes {
    THOUSAND(1000),
    FIVE_HUNDRED(500),
    ONE_HUNDRED(100),
    FIFTY(50),
    TWENTY(20);

    private int value;

    CurrencyTypes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
