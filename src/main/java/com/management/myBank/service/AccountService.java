package com.management.myBank.service;

import com.management.myBank.dto.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface AccountService {
    void createAccount(CustomerDto customerModel);

    CustomerDto getCustomerByMobileNumber(String mobileNumber);

    void updateAccount(CustomerDto customerModel);

    void deleteAccount(String mobileNumber);

    List<CustomerDto> getAllCustomers();

    CustomerDto getCustomerById(Long customerId);
}
