package com.hbpaymentprocessing.hbpaymentprocessing.repositories;

import com.hbpaymentprocessing.hbpaymentprocessing.entities.AccountTransactionn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<AccountTransactionn, Long> {

    @Query(nativeQuery = true,
            value = "select t.transaction_code " +
                    "from account_transactionn t " +
                    "where t.id = (" +
                    "select max(tr.id) " +
                    "from account_transactionn tr" +
                    ")")
    public String getLastTransactionCode();
}
