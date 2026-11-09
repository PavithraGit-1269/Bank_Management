package com.management.myBank.repository;

import com.management.myBank.dto.AccountDto;
import com.management.myBank.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<Accounts, Long> {
    Accounts findByCustomer_CustomerId(Long customerId);
}
