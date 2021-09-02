package com.web.controller;

import com.service.AtmService;
import com.util.Constant;
import com.web.exception.InsufficientBalanceException;
import com.web.exception.InvalidAmountException;
import com.web.model.AtmRequest;
import com.web.model.Currency;
import com.web.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class AtmServiceController {

    @Autowired
    AtmService atmService;

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @GetMapping("/initialize")
    public ResponseEntity<Response> initialize(@RequestBody AtmRequest request) {
        Response response = new Response();

        try {
            List<Currency> Currencies = atmService.initialize(request);
            response.setResponseStatus(Constant.SUCCESS);
            response.setResponseDesc(Constant.SUCCESS);
            response.setResponseBody(Currencies);
        } catch (Exception e) {
            response.setResponseStatus(Constant.FAIL);
            response.setResponseDesc("Unknown error");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @GetMapping("/withdraw")
    public ResponseEntity<Response> withdraw(@RequestParam("amount") int amount) {

        Response response = new Response();
        try {
            List<Currency> Currencies = atmService.withdraw(amount);
            response.setResponseStatus(Constant.SUCCESS);
            response.setResponseDesc(Constant.SUCCESS);
            response.setResponseBody(Currencies);

        }  catch (InsufficientBalanceException | InvalidAmountException e) {
            response.setResponseStatus(Constant.FAIL);
            response.setResponseDesc(e.getMessage());
        } catch (Exception e) {
            response.setResponseStatus(Constant.FAIL);
            response.setResponseDesc("Unknown error");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @GetMapping("/balance")
    public ResponseEntity<Response> checkBalance() {
        Response response = new Response();

        try {
            List<Currency> Currencies = atmService.checkBalance();
            response.setResponseStatus(Constant.SUCCESS);
            response.setResponseDesc(Constant.SUCCESS);
            response.setResponseBody(Currencies);
        } catch (Exception e) {
            response.setResponseStatus(Constant.FAIL);
            response.setResponseDesc("Unknown error");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
