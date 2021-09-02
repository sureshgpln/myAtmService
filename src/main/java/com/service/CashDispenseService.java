package com.service;


import com.entity.Currency;
import com.repository.CurrencyRepository;
import com.web.model.AtmRequest;
import com.web.model.ResponseWrapper;
import com.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Component
@Transactional
public class CashDispenseService {

    private final Logger logger = LoggerFactory.getLogger(CashDispenseService.class);

    @Autowired
    @Qualifier("CurrencyRepo")
    private CurrencyRepository currencyRepository;

    public ResponseEntity<ResponseWrapper> initialize(AtmRequest request){
        ResponseWrapper responseWrapper = new ResponseWrapper();

        responseWrapper.setResponseCode(Constant.SUCCESS_CODE);
        responseWrapper.setResponseDesc(Constant.SUCCESS);
        responseWrapper.setResponseStatus(Constant.SUCCESS);

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

            currency.setCurrency_count(request.getCurrencies().get(i).getCurrency_count() + currency.getCurrency_count());
            currencyRepository.saveAndFlush(currency);
        }

        ResponseEntity<ResponseWrapper> response = new ResponseEntity<>(responseWrapper, HttpStatus.OK);

        return response;
    }

    public ResponseEntity<ResponseWrapper> withdraw(int amount){
        //validate the requested amount with the min value

        // get all remaining currencies and their amounts from repo
        List<Currency> availableCurrenciesInAscOrder = currencyRepository.findAll();
        List<Currency> dispatchCurrencies = new ArrayList<>();

        int remainingAmount = amount;

        for (int i=0; i < availableCurrenciesInAscOrder.size(); i++) {

            Currency availableCurrency = availableCurrenciesInAscOrder.get(i);
            int availableCurrencyCount = availableCurrenciesInAscOrder.get(i).getCurrency_count();

            Currency dispatchCurrency = new Currency();
            dispatchCurrency.setCurrency_type(availableCurrency.getCurrency_type());
            dispatchCurrency.setCurrency_value(availableCurrency.getCurrency_value());

            int dispatchCurrencyCount = 0;

            if (remainingAmount >= availableCurrency.getCurrency_value()
                    && (remainingAmount % availableCurrency.getCurrency_value() == 0)
                    && availableCurrency.getCurrency_count() > (remainingAmount/availableCurrency.getCurrency_value())) {

                for (int j = 0; j < availableCurrencyCount && remainingAmount!=0; j++) {

                    dispatchCurrencyCount++;
                    availableCurrencyCount--;
                    remainingAmount = remainingAmount - availableCurrency.getCurrency_value();
                }

                dispatchCurrency.setCurrency_count(dispatchCurrencyCount);
                dispatchCurrencies.add(dispatchCurrency);
                availableCurrenciesInAscOrder.get(i).setCurrency_count(availableCurrencyCount);
            }
        }

        //save the updated available currencies back to DB
        currencyRepository.saveAll(availableCurrenciesInAscOrder);

        //prepare and return response object
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setResponseBody(convertToModel(dispatchCurrencies));
        responseWrapper.setResponseCode(Constant.SUCCESS_CODE);
        responseWrapper.setResponseDesc(Constant.SUCCESS);
        responseWrapper.setResponseStatus(Constant.SUCCESS);
        ResponseEntity<ResponseWrapper> response = new ResponseEntity<>(responseWrapper, HttpStatus.OK);

        return response;
    }

    public List<com.web.model.Currency> convertToModel (List<Currency> entityCurrencies) {
        List<com.web.model.Currency> modalCurrencies = new ArrayList<>();

        for(int i=0; i < entityCurrencies.size(); i++) {
            com.web.model.Currency modalCurrency = new com.web.model.Currency();
            modalCurrency.setCurrency_type(entityCurrencies.get(i).getCurrency_type());
            modalCurrency.setCurrency_count(entityCurrencies.get(i).getCurrency_count());
            modalCurrencies.add(modalCurrency);
        }

        return modalCurrencies;
    }


    public ResponseEntity<ResponseWrapper> checkBalance() {
        List<Currency> availableCurrencies = currencyRepository.findAll();

        ResponseWrapper responseWrapper = new ResponseWrapper();

        responseWrapper.setResponseBody(convertToModel(availableCurrencies));
        responseWrapper.setResponseCode(Constant.SUCCESS_CODE);
        responseWrapper.setResponseDesc(Constant.SUCCESS);
        responseWrapper.setResponseStatus(Constant.SUCCESS);

        ResponseEntity<ResponseWrapper> response = new ResponseEntity<>(responseWrapper, HttpStatus.OK);
        logger.info("Response {}", response);
        return response;
    }
}
