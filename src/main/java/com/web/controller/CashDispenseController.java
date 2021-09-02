package com.web.controller;

import com.service.CashDispenseService;
import com.web.exception.InsufficientBalanceException;
import com.web.exception.InsufficientNoteException;
import com.web.exception.InvalidRequestException;
import com.web.model.AtmRequest;
import com.web.model.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class CashDispenseController {

    @Autowired
    CashDispenseService cashDispenseService;

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @GetMapping("/dispense")
    public ResponseEntity<ResponseWrapper> deposit(
            @RequestParam("amount") int amount)
            throws InsufficientNoteException, InvalidRequestException, InsufficientBalanceException {
        return cashDispenseService.withdraw(amount);
    }

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @GetMapping("/checkBalance")
    public ResponseEntity<ResponseWrapper> checkBalance() {
        return cashDispenseService.checkBalance();
    }

    @CrossOrigin(origins = "*", allowCredentials = "true")
    @GetMapping("/initialize")
    public ResponseEntity<ResponseWrapper> initialize(@RequestBody AtmRequest request) {
        return cashDispenseService.initialize(request);
    }

}
