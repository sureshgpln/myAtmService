package com;

import com.repository.CurrencyRepository;
import com.service.AtmService;
import com.util.CurrencyTypes;
import com.web.exception.InsufficientBalanceException;
import com.web.exception.InvalidAmountException;
import com.web.model.AtmRequest;
import com.web.model.Currency;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AtmServiceTest {

    @Mock
    CurrencyRepository mockCurrencyRepository;

    @InjectMocks
    AtmService atmService;

    @Before
    public void setUp(){

    }

    List<Currency> getCurrencies(int count1000, int count500, int count100, int count50, int count20){
        List<Currency> currencies = new ArrayList<>();
        currencies.add(new Currency(CurrencyTypes.THOUSAND, count1000));
        currencies.add(new Currency(CurrencyTypes.FIVE_HUNDRED, count500));
        currencies.add(new Currency(CurrencyTypes.ONE_HUNDRED, count100));
        currencies.add(new Currency(CurrencyTypes.FIFTY, count50));
        currencies.add(new Currency(CurrencyTypes.TWENTY, count20));
        return currencies;
    }

    List<com.entity.Currency> convertToCurrencyEntities(List<Currency> modelCurrencies) {
        List<com.entity.Currency> entityCurrencies = new ArrayList<>();

        for (Currency modelCurrency : modelCurrencies) {
            com.entity.Currency entityCurrency = new com.entity.Currency();
            entityCurrency.setCurrency_type(modelCurrency.getCurrency_type());
            entityCurrency.setCurrency_value(modelCurrency.getCurrency_type().getValue());
            entityCurrency.setCurrency_count(modelCurrency.getCurrency_count());
            entityCurrencies.add(entityCurrency);
        }

        return entityCurrencies;
    }

    @Test
    public void withdrawWhenSuccess() throws InsufficientBalanceException, InvalidAmountException {

        List<Currency> currencies = getCurrencies(20, 20, 20, 20, 20);
        when(mockCurrencyRepository.findAll()).thenReturn(convertToCurrencyEntities(currencies));
        Assert.assertEquals(1, atmService.withdraw(200).size());
        Assert.assertEquals(3, atmService.withdraw(190).size());
        Assert.assertEquals(2, atmService.withdraw(180).size());
        Assert.assertEquals(3, atmService.withdraw(170).size());
        Assert.assertEquals(2, atmService.withdraw(160).size());
        Assert.assertEquals(2, atmService.withdraw(150).size());
        Assert.assertEquals(2, atmService.withdraw(140).size());

        Assert.assertEquals(2, atmService.withdraw(120).size());

        Assert.assertEquals(1, atmService.withdraw(100).size());
        Assert.assertEquals(2, atmService.withdraw(90).size());
        Assert.assertEquals(1, atmService.withdraw(80).size());
        Assert.assertEquals(2, atmService.withdraw(70).size());
    }

    @Test
    public void withdrawWhenFail() {

        List<Currency> currencies = getCurrencies(20, 20, 20, 20, 20);
        when(mockCurrencyRepository.findAll()).thenReturn(convertToCurrencyEntities(currencies));
        try {
        atmService.withdraw(10);
        } catch (Exception e) {
            Assert.assertEquals("Please enter a valid amount", e.getMessage());
        }

        try {
            atmService.withdraw(135);
        } catch (Exception e) {
            Assert.assertEquals("Funds not available for the amount requested, please try later", e.getMessage());
        }

        try {
            atmService.withdraw(110);
        } catch (Exception e) {
            Assert.assertEquals("Funds not available for the amount requested, please try later", e.getMessage());
        }

        try {
            atmService.withdraw(114);
        } catch (Exception e) {
            Assert.assertEquals("Funds not available for the amount requested, please try later", e.getMessage());
        }

    }

    @Test
    public void withdraw200WithLimitedCurrency() throws InsufficientBalanceException, InvalidAmountException {

/*
        List<Currency> currencies = getCurrencies(0, 0, 0, 3, 8);
        when(mockCurrencyRepository.findAll()).thenReturn(convertToCurrencyEntities(currencies));
        Assert.assertEquals(1, atmService.withdraw(200).size());
*/

    }
}
