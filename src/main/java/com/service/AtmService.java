package com.service;


import com.entity.Currency;
import com.repository.CurrencyRepository;
import com.util.CurrencyTypes;
import com.web.exception.InsufficientBalanceException;
import com.web.exception.InvalidAmountException;
import com.web.model.AtmRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


@Component
@Transactional
public class AtmService {

    private final Logger logger = LoggerFactory.getLogger(AtmService.class);

    @Autowired
    @Qualifier("CurrencyRepo")
    private CurrencyRepository currencyRepository;

    public List<com.web.model.Currency> initialize(AtmRequest request){
        for (int i = 0; i < request.getCurrencies().size(); i++) {

            if (request.getCurrencies().get(i) == null){
                continue;
            }

            Currency currency = currencyRepository.fetchCurrencyByValue(request.getCurrencies().get(i).getCurrency_type());

            if (currency == null){
                currency = new Currency();
                currency.setCurrency_type(request.getCurrencies().get(i).getCurrency_type());
                currency.setCurrency_value(request.getCurrencies().get(i).getCurrency_type().getValue());
            }

            currency.setCurrency_count(request.getCurrencies().get(i).getCurrency_count());
            currencyRepository.saveAndFlush(currency);
        }

        return convertToModel(currencyRepository.findAll());
    }

    public List<com.web.model.Currency> withdraw(int amount) throws InsufficientBalanceException, InvalidAmountException {
        //validate the requested amount with the min value
        if (amount < CurrencyTypes.TWENTY.getValue()) {
            throw new InvalidAmountException("Please enter a valid amount");
        }

        // get all remaining currencies and their amounts from repo
        List<Currency> availableCurrencies = currencyRepository.findAll();
        List<Currency> dispatchCurrencies = new ArrayList<>();

        int remainingAmount = amount;

        for (int i=0; i < availableCurrencies.size() && remainingAmount > 0; i++) {

            Currency availableCurrency = availableCurrencies.get(i);
            int availableCurrencyCount = availableCurrency.getCurrency_count();
            int availableCurrencyValue = availableCurrency.getCurrency_value();

            Currency dispatchCurrency = new Currency();
            dispatchCurrency.setCurrency_type(availableCurrency.getCurrency_type());
            dispatchCurrency.setCurrency_value(availableCurrency.getCurrency_value());

            int dispatchCurrencyCount = 0;

            for (int j = 0;(j < availableCurrency.getCurrency_count()) && (remainingAmount >= availableCurrencyValue);j++) {

                //currency 50 should not be issued if the remaining amount is even and <=100, instead 20 should be used
                if ((CurrencyTypes.FIFTY.getValue() == availableCurrencyValue)
                        && (remainingAmount <= 100)
                        && ((remainingAmount/10) % 2 == 0)){
                    break;
                }

                dispatchCurrencyCount++;
                availableCurrencyCount--;
                remainingAmount = remainingAmount - availableCurrency.getCurrency_value();

            }

            if (dispatchCurrencyCount > 0) {
                dispatchCurrency.setCurrency_count(dispatchCurrencyCount);
                dispatchCurrencies.add(dispatchCurrency);
                availableCurrencies.get(i).setCurrency_count(availableCurrencyCount);
            }
        }

        if (remainingAmount == 0) {
            currencyRepository.saveAll(availableCurrencies);
        } else {
            throw new InsufficientBalanceException("Funds not available for the amount requested, please try later");
        }

        return convertToModel(dispatchCurrencies);
    }

    private List<com.web.model.Currency> convertToModel(List<Currency> entityCurrencies) {
        List<com.web.model.Currency> modalCurrencies = new ArrayList<>();

        for (Currency entityCurrency : entityCurrencies) {
            com.web.model.Currency modalCurrency = new com.web.model.Currency();
            modalCurrency.setCurrency_type(entityCurrency.getCurrency_type());
            modalCurrency.setCurrency_count(entityCurrency.getCurrency_count());
            modalCurrencies.add(modalCurrency);
        }

        return modalCurrencies;
    }


    public List<com.web.model.Currency> checkBalance() {
        List<Currency> availableCurrencies = currencyRepository.findAll();
        return convertToModel(availableCurrencies);
    }
}
