package com.hbpaymentprocessing.hbpaymentprocessing.controllers;

import com.hbpaymentprocessing.hbpaymentprocessing.services.TransactionServiceImpl;
import com.hbpaymentprocessing.hbpaymentprocessing.utilities.JWTUtil;
import com.hbpaymentprocessing.hbpaymentprocessing.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @Autowired
    private Utility utility;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping(value = "/invoice-payment")
    public ResponseEntity<?> checkout(
            @RequestParam(name = "invoiceCode", required = true) String invoiceCode,
            @RequestParam(name = "total", required = true) String total,
            @RequestParam(name = "description", required = true) String description,
            @RequestParam(name = "tagName", required = false) String tagName,
            @RequestParam(name = "senderAccountNumberCode", required = true) String senderAccountNumberCode,
            @RequestParam(name = "receiverAccountNumberCode", required = true) String receiverAccountNumberCode,
            HttpServletRequest request) {
        try {
            String jwtString = jwtUtil.getJWTStringFromHeader(request);
            return ResponseEntity.ok(transactionServiceImpl.insertTransaction(jwtUtil.getEmailFromJWTString(
                            jwtString.trim()), jwtUtil.getRoleID(jwtString.trim()), invoiceCode, total, description,
                    tagName, senderAccountNumberCode, receiverAccountNumberCode));
        } catch (NoSuchAlgorithmException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("err_message", ex.getMessage()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("err_message", ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("err_message", ex.getMessage()));
        }
    }
}
