package com.hbpaymentprocessing.hbpaymentprocessing.repositories;

import com.hbpaymentprocessing.hbpaymentprocessing.entities.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountStatusRepository extends JpaRepository<AccountStatus, Long> {

    public AccountStatus findByName(String name);
}
