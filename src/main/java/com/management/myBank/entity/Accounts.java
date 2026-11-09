package com.management.myBank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Accounts {
    @Id
    @Column(name = "account_number")
    private Long accountNumber;

    @OneToOne
    @JoinColumn(name = "customer_id", unique = true)
    private Customer customer;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "branch_address")
    private String branchAddress;
}
