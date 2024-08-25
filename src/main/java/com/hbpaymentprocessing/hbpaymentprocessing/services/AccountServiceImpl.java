package com.hbpaymentprocessing.hbpaymentprocessing.services;

import com.hbpaymentprocessing.hbpaymentprocessing.entities.Account;
import com.hbpaymentprocessing.hbpaymentprocessing.entities.AccountStatus;
import com.hbpaymentprocessing.hbpaymentprocessing.entities.Role;
import com.hbpaymentprocessing.hbpaymentprocessing.input.AccountLoginRequest;
import com.hbpaymentprocessing.hbpaymentprocessing.input.AccountRegistrationRequest;
import com.hbpaymentprocessing.hbpaymentprocessing.ouputs.AuthenticatedAccountResponse;
import com.hbpaymentprocessing.hbpaymentprocessing.repositories.AccountRepository;
import com.hbpaymentprocessing.hbpaymentprocessing.repositories.AccountStatusRepository;
import com.hbpaymentprocessing.hbpaymentprocessing.repositories.RoleRepository;
import com.hbpaymentprocessing.hbpaymentprocessing.utilities.ConstantVariables;
import com.hbpaymentprocessing.hbpaymentprocessing.utilities.JWTUtil;
import com.hbpaymentprocessing.hbpaymentprocessing.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.DateTimeException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountStatusRepository accountStatusRepository;

    @Autowired
    private Utility utility;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Transactional(rollbackFor = {NumberFormatException.class, NoSuchAlgorithmException.class,
            DateTimeException.class, IllegalArgumentException.class, RuntimeException.class})
    @Override
    public String register(AccountRegistrationRequest accountRegistrationRequest)
            throws NoSuchAlgorithmException, NumberFormatException, IllegalArgumentException, RuntimeException {

        Account accountEntity = accountRepository.findByEmail(accountRegistrationRequest.getEmail().trim());
        if (accountEntity != null) {
            throw new RuntimeException("The account existed");
        }

        Role roleEntity = roleRepository.findByName(accountRegistrationRequest.getRoleName().trim());
        AccountStatus accountStatusEntity = accountStatusRepository
                .findByName(ConstantVariables.ACTIVE_STATUS.trim());

        Timestamp currentDateTime = utility.getCurrentDateTime();

        // hash and encrypt password
        String hashedPassword = utility.encryptPassword(accountRegistrationRequest.getPwd().trim(),
                ConstantVariables.PASSWORD_HASHING_ALGORITHM_NAME.trim());
        accountRegistrationRequest.setPwd(passwordEncoder.encode(hashedPassword.trim()).trim());

        Account entity = new Account();
        entity.setEmail(accountRegistrationRequest.getEmail().trim());
        entity.setPwd(accountRegistrationRequest.getPwd().trim());
        entity.setFullName(accountRegistrationRequest.getFullName().trim());
        if (accountRegistrationRequest.getPhoneNumber() != null) {
            entity.setPhoneNumber(accountRegistrationRequest.getPhoneNumber());
        }
        if (accountRegistrationRequest.getAddress() != null) {
            entity.setAddress(accountRegistrationRequest.getAddress().trim());
        }
        entity.setIdCard(accountRegistrationRequest.getIdCard().trim());
        entity.setRole(roleEntity);
        entity.setAccountStatus(accountStatusEntity);
        entity.setCreatedDate(currentDateTime);
        entity.setModifiedDate(currentDateTime);
        entity.setDeletedDate(currentDateTime);


        Account result = accountRepository.save(entity);
        return result.getEmail().trim()
                .equals(accountRegistrationRequest.getEmail().trim())
                ? "Successful registration" : "Failed registration";
    }

    private void authenticateAccount(String email, String pwd) throws AuthenticationException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(email.trim(), pwd.trim());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    public AuthenticatedAccountResponse login(AccountLoginRequest accountLoginRequest) throws AuthenticationException, Exception {
        String email = accountLoginRequest.getEmail();
        String pwd = utility.encryptPassword(accountLoginRequest.getPwd().trim(),
                ConstantVariables.PASSWORD_HASHING_ALGORITHM_NAME.trim());

        this.authenticateAccount(email, pwd);
        Account accountEntity = accountRepository.findByEmail(email.trim());
        if (accountEntity == null) {
            throw new RuntimeException("Not found this account");
        }
        if (!passwordEncoder.matches(pwd.trim(), accountEntity.getPwd().trim())) {
            throw new RuntimeException("Not found this account");
        }
        Long roleId = accountEntity.getRole().getId();
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("roleId", roleId);
        claimsMap.put("fullName", accountEntity.getFullName().trim());
        return new AuthenticatedAccountResponse(jwtUtil.getPublicKeyAsString(),
                jwtUtil.createJWTString(email.trim(), claimsMap));
    }

    @Override
    public Account checkEmailAndRoleID(String email, Long roleID) throws RuntimeException {
        Account accountInformation = accountRepository.findByEmailAndRoleID(email.trim(), roleID);
        if (accountInformation == null) {
            throw new RuntimeException("Not found this account");
        } else {
            return accountInformation;
        }
    }
}
