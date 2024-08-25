package com.hbpaymentprocessing.hbpaymentprocessing.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Account")
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", length = 250, nullable = false, unique = true)
    private String email;

    @Column(name = "pwd", length = 250, nullable = false)
    private String pwd;

    @Column(name = "full_name", nullable = false, columnDefinition = "nvarchar(250)")
    private String fullName;

    @Column(name = "phone_number", length = 250)
    private String phoneNumber;

    @Column(name = "address", nullable = true, columnDefinition = "nvarchar(250)")
    private String address;

    @Column(name = "id_card", length = 12, nullable = false)
    private String idCard;

    @Column(name = "created_date", nullable = false, columnDefinition = "datetime")
    private Timestamp createdDate;

    @Column(name = "modified_date", nullable = false, columnDefinition = "datetime")
    private Timestamp modifiedDate;

    @Column(name = "deleted_date", nullable = false, columnDefinition = "datetime")
    private Timestamp deletedDate;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountTransactionn> accountTransactionns = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountNumberCode> accountNumberCodes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_status_id")
    private AccountStatus accountStatus;


}
