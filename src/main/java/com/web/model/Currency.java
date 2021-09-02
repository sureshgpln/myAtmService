package com.web.model;

import com.util.CurrencyTypes;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Currency {

    private CurrencyTypes currency_type;
    private Integer currency_count;

    public Currency() {}

    public Currency(CurrencyTypes type, int count) {
        this.currency_type = type;
        this.currency_count = count;
    }
}
