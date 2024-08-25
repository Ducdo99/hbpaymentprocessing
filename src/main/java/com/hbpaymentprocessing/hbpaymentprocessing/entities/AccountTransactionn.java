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

@Entity(name = "AccountTransactionn")
@Table(name = "account_transactionn")
@Setter
@Getter
@NoArgsConstructor
public class AccountTransactionn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "transaction_code", length = 21, nullable = false, unique = true)
    private String transactionCode;

    @Column(name = "invoice_code", length = 20, nullable = false, unique = true)
    private String invoiceCode;

    @Column(name = "receiver_account_number_code", length = 12, nullable = false)
    private String receiverAccountNumberCode;

    @Column(name = "sender_account_number_code", length = 12, nullable = false)
    private String senderAccountNumberCode;

    @Column(name = "payment_date", nullable = false, columnDefinition = "datetime")
    private Timestamp paymentDate;

    @Column(name = "total", nullable = false)
    private Double total;

    @Column(name = "description", length = 250, nullable = false)
    private String description;

    @Column(name = "created_date", nullable = false, columnDefinition = "datetime")
    private Timestamp createdDate;

    @Column(name = "modified_date", nullable = false, columnDefinition = "datetime")
    private Timestamp modifiedDate;

    @Column(name = "deleted_date", nullable = false, columnDefinition = "datetime")
    private Timestamp deletedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_tag_id")
    private AccountTransactionnTag accountTransactionnTag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_status_id")
    private AccountTransactionnStatus accountTransactionnStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
}
