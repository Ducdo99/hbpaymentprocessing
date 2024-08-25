package com.hbpaymentprocessing.hbpaymentprocessing.repositories;

import com.hbpaymentprocessing.hbpaymentprocessing.entities.AccountTransactionnStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionStatusRepository extends JpaRepository<AccountTransactionnStatus, Long> {
    @Query(nativeQuery = true,
            value = "select * " +
                    "from account_transactionn_status ts " +
                    "where ts.name like :sttName"
    )
    public AccountTransactionnStatus getTransactionStatusId(@Param(value = "sttName") String sttName);
}
