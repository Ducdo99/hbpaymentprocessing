package com.hbpaymentprocessing.hbpaymentprocessing.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "AccountTransactionnTag")
@Table(name = "account_transactionn_tag")
@Setter
@Getter
@NoArgsConstructor
public class AccountTransactionnTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 13, unique = true, nullable = false)
    private String name;

    @Column(name = "created_date", nullable = false, columnDefinition = "datetime")
    private Timestamp createdDate;

    @Column(name = "modified_date", nullable = false, columnDefinition = "datetime")
    private Timestamp modifiedDate;

    @Column(name = "deleted_date", nullable = false, columnDefinition = "datetime")
    private Timestamp deletedDate;

    @OneToMany(mappedBy = "accountTransactionnTag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountTransactionn> accountTransactionns = new ArrayList<>();
}
