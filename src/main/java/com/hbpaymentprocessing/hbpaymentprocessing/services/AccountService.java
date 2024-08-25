package com.hbpaymentprocessing.hbpaymentprocessing.services;

import com.hbpaymentprocessing.hbpaymentprocessing.entities.Account;
import com.hbpaymentprocessing.hbpaymentprocessing.input.AccountLoginRequest;
import com.hbpaymentprocessing.hbpaymentprocessing.input.AccountRegistrationRequest;
import com.hbpaymentprocessing.hbpaymentprocessing.ouputs.AuthenticatedAccountResponse;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
public interface AccountService {

    public String register(AccountRegistrationRequest accountRegistrationRequest)
            throws IllegalArgumentException, NoSuchAlgorithmException, NumberFormatException, RuntimeException;

    public AuthenticatedAccountResponse login(AccountLoginRequest accountLoginRequest) throws Exception;

    public Account checkEmailAndRoleID(String email, Long roleID) throws RuntimeException;
}
