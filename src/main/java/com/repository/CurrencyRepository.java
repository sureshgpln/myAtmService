package com.repository;

import com.entity.Currency;
import com.util.CurrencyTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository ("CurrencyRepo")
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    @Query("select currency from Currency currency where currency.currency_type = :currency_type")
    Currency fetchCurrencyByValue(@Param("currency_type") CurrencyTypes value);

    Currency save (Currency currency);

    Currency saveAndFlush (Currency currency);
}
