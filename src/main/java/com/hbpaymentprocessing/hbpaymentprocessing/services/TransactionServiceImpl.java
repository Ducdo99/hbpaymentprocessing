package com.hbpaymentprocessing.hbpaymentprocessing.services;

import com.hbpaymentprocessing.hbpaymentprocessing.entities.Account;
import com.hbpaymentprocessing.hbpaymentprocessing.entities.AccountNumberCode;
import com.hbpaymentprocessing.hbpaymentprocessing.entities.AccountNumberCodeStatus;
import com.hbpaymentprocessing.hbpaymentprocessing.entities.AccountTransactionn;
import com.hbpaymentprocessing.hbpaymentprocessing.repositories.AccountNumberCodeRepository;
import com.hbpaymentprocessing.hbpaymentprocessing.repositories.AccountNumberCodeStatusRepository;
import com.hbpaymentprocessing.hbpaymentprocessing.repositories.TransactionRepository;
import com.hbpaymentprocessing.hbpaymentprocessing.repositories.TransactionStatusRepository;
import com.hbpaymentprocessing.hbpaymentprocessing.repositories.TransactionTagRepository;
import com.hbpaymentprocessing.hbpaymentprocessing.utilities.ConstantVariables;
import com.hbpaymentprocessing.hbpaymentprocessing.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionTagRepository transactionTagRepository;

    @Autowired
    private TransactionStatusRepository transactionStatusRepository;

    @Autowired
    private Utility utility;

    @Autowired
    private AccountServiceImpl accountServiceImpl;

    @Autowired
    private AccountNumberCodeRepository accountNumberCodeRepository;

    @Autowired
    private AccountNumberCodeStatusRepository accountNumberCodeStatusRepository;

    @Transactional(rollbackFor = {SQLException.class, IllegalArgumentException.class, NoSuchAlgorithmException.class,
            Throwable.class, Exception.class})
    @Override
    public boolean insertTransaction(String email, String roleId, String invoiceCode, String total, String description,
                                     String tagName, String senderAccountNumberCode, String receiverAccountNumberCode)
            throws NumberFormatException, IllegalArgumentException, NoSuchAlgorithmException {
        boolean isInserted = false;

        Account accountEntity = accountServiceImpl.checkEmailAndRoleID(email.trim(), Long.valueOf(roleId.trim()));

        AccountNumberCodeStatus accountNumberCodeStatusEntity =
                accountNumberCodeStatusRepository.findByName(ConstantVariables.ACTIVE_STATUS.trim());

        AccountNumberCode accountNumberCodeEntity =
                accountNumberCodeRepository.getAccountNumberCodeByAccountIdAndNumberCode(
                        accountEntity.getId(), senderAccountNumberCode.trim(),
                        accountNumberCodeStatusEntity.getId());
        if (accountNumberCodeEntity != null) {

            AccountTransactionn accountTransactionnEntity = new AccountTransactionn();
            accountTransactionnEntity.setInvoiceCode(invoiceCode.trim());
            accountTransactionnEntity.setSenderAccountNumberCode(senderAccountNumberCode.trim());
            accountTransactionnEntity.setReceiverAccountNumberCode(receiverAccountNumberCode.trim());
            accountTransactionnEntity.setDescription(description.trim());
            accountTransactionnEntity.setTotal(Double.valueOf(total.trim()));

            String lastTransactionCode = transactionRepository.getLastTransactionCode();
            accountTransactionnEntity.setTransactionCode(utility.increaseCode(lastTransactionCode).trim());
            accountTransactionnEntity.setPaymentDate(utility.getCurrentDateTime());

            if (tagName != null) {
                if (!tagName.isEmpty()) {
                    accountTransactionnEntity.setAccountTransactionnTag(
                            transactionTagRepository.getTransactionTagIdByName(tagName.trim()));
                }
            }
            accountTransactionnEntity.setAccount(accountEntity);
            accountTransactionnEntity.setAccountTransactionnStatus(transactionStatusRepository.getTransactionStatusId(
                    ConstantVariables.PAID_TRANSACTION_STATUS.trim()));

            accountTransactionnEntity.setCreatedDate(utility.getCurrentDateTime());
            accountTransactionnEntity.setModifiedDate(utility.getCurrentDateTime());
            accountTransactionnEntity.setDeletedDate(utility.getCurrentDateTime());
            accountTransactionnEntity = transactionRepository.save(accountTransactionnEntity);
            if (accountTransactionnEntity.getId() != null) {
                if (accountTransactionnEntity.getId() > 0) {
                    isInserted = true;
                }
            } // end if entity is inserted
        }
        return isInserted;
    }
}
