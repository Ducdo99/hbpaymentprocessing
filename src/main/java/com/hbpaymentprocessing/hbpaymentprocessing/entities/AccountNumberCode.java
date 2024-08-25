package com.hbpaymentprocessing.hbpaymentprocessing.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity(name = "AccountNumberCode")
@Table(name = "account_number_code")
@Getter
@Setter
@NoArgsConstructor
public class AccountNumberCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number_code", length = 12, nullable = false)
    private String numberCode;

    @Column(name = "created_date", nullable = false, columnDefinition = "datetime")
    private Timestamp createdDate;

    @Column(name = "modified_date", nullable = false, columnDefinition = "datetime")
    private Timestamp modifiedDate;

    @Column(name = "deleted_date", nullable = false, columnDefinition = "datetime")
    private Timestamp deletedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_number_code_status_id")
    private AccountNumberCodeStatus accountNumberCodeStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
}
