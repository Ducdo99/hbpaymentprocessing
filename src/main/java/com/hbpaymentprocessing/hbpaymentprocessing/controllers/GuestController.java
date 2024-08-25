package com.hbpaymentprocessing.hbpaymentprocessing.controllers;

import com.hbpaymentprocessing.hbpaymentprocessing.input.AccountLoginRequest;
import com.hbpaymentprocessing.hbpaymentprocessing.input.AccountRegistrationRequest;
import com.hbpaymentprocessing.hbpaymentprocessing.services.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Collections;

@RestController
@RequestMapping(value = "/guest")
public class GuestController {

    @Autowired
    private AccountServiceImpl accountServiceImpl;

    @PostMapping(value = "/login")
    public ResponseEntity<?> loginAccount(@RequestBody(required = true)AccountLoginRequest accountLoginRequest) {
        try {
            return ResponseEntity.ok(accountServiceImpl.login(accountLoginRequest));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("err_message", ex.getMessage()));
        } catch (NoSuchAlgorithmException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("err_message", ex.getMessage()));
        } catch (SQLException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("err_message", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("err_message", ex.getMessage()));
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerAccount(
            @RequestBody(required = true) AccountRegistrationRequest accountRegistrationRequest) {
        try {
            return ResponseEntity.ok(Collections.singletonMap("message",
                    accountServiceImpl.register(accountRegistrationRequest)));
        } catch (NoSuchAlgorithmException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("err_message", ex.getMessage()));
        } catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("err_message", ex.getMessage()));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("err_message", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("err_message", ex.getMessage()));
        }
    }

}
