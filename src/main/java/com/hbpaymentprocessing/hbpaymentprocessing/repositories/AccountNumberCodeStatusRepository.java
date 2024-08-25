package com.hbpaymentprocessing.hbpaymentprocessing.repositories;

import com.hbpaymentprocessing.hbpaymentprocessing.entities.AccountNumberCodeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountNumberCodeStatusRepository extends JpaRepository<AccountNumberCodeStatus, Integer> {

    public AccountNumberCodeStatus findByName(String name);
}
