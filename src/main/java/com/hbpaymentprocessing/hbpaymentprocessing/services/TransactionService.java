package com.hbpaymentprocessing.hbpaymentprocessing.services;

import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
public interface TransactionService {

    public boolean insertTransaction(String email, String roleId, String invoiceCode, String total, String description,
                                     String tagName, String senderAccountNumberCode, String receiverAccountNumberCode)
            throws NumberFormatException, IllegalArgumentException, NoSuchAlgorithmException;

}
