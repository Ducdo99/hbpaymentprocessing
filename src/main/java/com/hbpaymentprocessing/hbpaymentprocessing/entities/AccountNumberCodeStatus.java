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

@Entity(name = "AccountNumberCodeStatus")
@Table(name = "account_number_code_status")
@Getter
@Setter
@NoArgsConstructor
public class AccountNumberCodeStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 15, nullable = false)
    private String name;

    @Column(name = "created_date", nullable = false, columnDefinition = "datetime")
    private Timestamp createdDate;

    @Column(name = "modified_date", nullable = false, columnDefinition = "datetime")
    private Timestamp modifiedDate;

    @Column(name = "deleted_date", nullable = false, columnDefinition = "datetime")
    private Timestamp deletedDate;

    @OneToMany(mappedBy = "accountNumberCodeStatus", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountNumberCode> accountNumberCodes = new ArrayList<>();
}
