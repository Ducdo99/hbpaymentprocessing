package com.hbpaymentprocessing.hbpaymentprocessing.repositories;

import com.hbpaymentprocessing.hbpaymentprocessing.entities.AccountNumberCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountNumberCodeRepository extends JpaRepository<AccountNumberCode, Long> {
    @Query(nativeQuery = true,
            value = "select accNumCode " +
                    "from account_number_code accNumCode " +
                    "where accNumCode.account_id = :accId " +
                    "and accNumCode.account_number_code_status_id = :statusId")
    public List<AccountNumberCode> getAllAccountNumberCodeByAccountId(@Param("accId") Long accId,
                                                                      @Param("statusId") Integer statusId);

    @Query(nativeQuery = true,
            value = "select * " +
                    "from account_number_code accNumCode " +
                    "where accNumCode.account_id = :accId " +
                    "and accNumCode.number_code like :numberCode " +
                    "and accNumCode.account_number_code_status_id = :statusId")
    public AccountNumberCode getAccountNumberCodeByAccountIdAndNumberCode(@Param("accId") Long accId,
                                                                          @Param("numberCode") String numberCode,
                                                                          @Param("statusId") Integer statusId);
}
