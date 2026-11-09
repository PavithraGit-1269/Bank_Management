package com.management.myBank.mapper;

import com.management.myBank.dto.AccountDto;
import com.management.myBank.dto.CustomerDto;
import com.management.myBank.entity.Accounts;

public class AccountMapper {
    public static Accounts customerDtoToAccountEntity(CustomerDto customerDto)
    {
        return Accounts.builder().accountType(customerDto.getAccountsDto().getAccountType())
                .branchAddress(customerDto.getAccountsDto().getBranchAddress()).build();

    }

    public static AccountDto accountEntityToAccountDto(Accounts account)
    {
        return AccountDto.builder()
                .customerId(account.getCustomer().getCustomerId())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .branchAddress(account.getBranchAddress())
                .accountNumber(account.getAccountNumber())
                .build();
    }
    public static void updateAccountEntity(AccountDto accountsDto, Accounts account)
    {
        account.setAccountType(accountsDto.getAccountType());
        account.setBranchAddress(accountsDto.getBranchAddress());
    }
}
