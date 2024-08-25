package com.hbpaymentprocessing.hbpaymentprocessing.repositories;

import com.hbpaymentprocessing.hbpaymentprocessing.entities.AccountTransactionnTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTagRepository extends JpaRepository<AccountTransactionnTag, Long> {

    @Query(nativeQuery = true,
            value = "select * " +
            "from account_transactionn_tag tt " +
            "where tt.name like :tagName"
    )
    public AccountTransactionnTag getTransactionTagIdByName(@Param(value = "tagName") String tagName);
}
