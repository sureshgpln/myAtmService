package com.entity;

import com.util.CurrencyTypes;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "currency")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "CURRENCY_TYPE")
    @Enumerated(EnumType.STRING)
    private CurrencyTypes currency_type;

    @Column(name = "CURRENCY_VALUE")
    private int currency_value;

    @Column(name = "CURRENCY_COUNT")
    private int currency_count;

}
