package com;

import com.repository.CurrencyRepository;
import com.service.CashDispenseService;
import com.database.DatabaseManager;
import com.web.model.ResponseWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CashDispenseServiceTest {

    @Mock
    DatabaseManager mockDatabaseManager;

    @Mock
    CurrencyRepository mockCurrencyRepository;

    @InjectMocks
    CashDispenseService cashDispenseService;

    final int[] bankAmount = {5, 5, 5, 5, 5};
    final int[] bankValues = {1000, 500, 100, 50, 20};

    /*final List<com.entity.Currency> currencies = new ArrayList<com.entity.Currency>(){
            {'1', CurrencyTypes.THOUSAND, 1000, 20};
            {'2', CurrencyTypes.FIVE_HUNDRED, 500, 20};
            {'3', CurrencyTypes.ONE_HUNDRED, 100, 20},
            {'4', CurrencyTypes.FIFTY, 50, 20},
            {'5', CurrencyTypes.TWENTY, 20, 20}
    };*/



    @Before
    public void setUp(){
        when(mockDatabaseManager.getBankAmount()).thenReturn(bankAmount);
        when(mockDatabaseManager.getBankValues()).thenReturn(bankValues);
        doNothing().when(mockDatabaseManager).updateBalanceAmt(any(int[].class), any(int[].class));
        /*when(mockCurrencyRepository.findAll()).thenReturn(currencies);*/
    }

    /*@Test
    public void subtractBankNumberCorrectly(){
        int[] usedBanks = {2, 1, 0, 2, 4};
        int[] balanceBanks = {5, 5, 5, 5, 5};
        int[] remainingBanks = {3, 4, 5, 3, 1};

        int[] result = cashDispenseService.subtractBankAmt(balanceBanks, usedBanks);

        Assert.assertArrayEquals(remainingBanks, result);
    }

    @Test
    public void calculateTotalBalanceCorrectly(){
        int[] remainingBanks = {1, 1, 1, 1, 1};
        int expectedAmt = 1670;
        int result = cashDispenseService.calCurrentTotalAmt(remainingBanks, bankValues);

        Assert.assertEquals(expectedAmt, result);
    }*/

    /*@Test
    public void findBanksCorrectly(){
        int[] currentBankAmt = new int[5];
        int[] balanceBankAmt = {10, 10, 10, 10, 10};

        List<int[]> expectedResult = new ArrayList<>();
        expectedResult.add(new int[]{0, 0, 1, 0, 4});
        expectedResult.add(new int[]{0, 0, 0, 2, 4});
        expectedResult.add(new int[]{0, 0, 0, 0, 9});

        List<int[]> result = cashDispenseService.findBanks(180, currentBankAmt, balanceBankAmt, bankValues, 0);

        Assert.assertEquals(expectedResult.size(), result.size());

        for (int i = 0; i < result.size(); i++){
            Assert.assertArrayEquals(result.get(i), expectedResult.get(i));
        }
    }*/

    @Test
    public void calculateBankWorkCorrectlyWhenSuccess(){

        String expectedResultCode = "0";
        String expectedResultDesc = "SUCCESS";

        ResponseEntity<ResponseWrapper> response = cashDispenseService.withdraw(180);

        Assert.assertEquals(expectedResultCode, response.getBody().getResponseCode());
        Assert.assertEquals(expectedResultDesc, response.getBody().getResponseDesc());
    }

    @Test
    public void calculateBankWorkCorrectlyWhenFail(){

        String expectedResultCode = "1";
        String expectedResultDesc = "Amount less than min amount";

        ResponseEntity<ResponseWrapper> response = cashDispenseService.withdraw(10);

        Assert.assertEquals(expectedResultCode, response.getBody().getResponseCode());
        Assert.assertEquals(expectedResultDesc, response.getBody().getResponseDesc());
    }
}
