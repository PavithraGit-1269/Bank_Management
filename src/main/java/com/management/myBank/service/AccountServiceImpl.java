package com.management.myBank.service;

import com.management.myBank.dto.CustomerDto;
import com.management.myBank.entity.Accounts;
import com.management.myBank.entity.Customer;
import com.management.myBank.exceptions.CustomerAlreadyExistsException;
import com.management.myBank.exceptions.ResourceNotFoundException;
import com.management.myBank.mapper.AccountMapper;
import com.management.myBank.mapper.CustomerMapper;
import com.management.myBank.repository.AccountRepo;
import com.management.myBank.repository.CustomerRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {
    private final CustomerRepo customerRepository;
    private final AccountRepo accountsRepository;

    public AccountServiceImpl(
            CustomerRepo customerRepository,
            AccountRepo accountsRepository) {
        this.customerRepository = customerRepository;
        this.accountsRepository = accountsRepository;
    }
    @Override
    @Transactional
    public void createAccount(CustomerDto customerDto)
    {
        Optional<Customer> customer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (customer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    + customerDto.getMobileNumber());
        }

        // Getting the data from model classes and converting it into Entity classes.
        Customer customerEntity = CustomerMapper.customerDtoToCustomerEntity(customerDto);
        // we are saving customerEntity in customer table
        Customer customerResults = customerRepository.save(customerEntity);
        // account number generation
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        Accounts accountsEntity = AccountMapper.customerDtoToAccountEntity(customerDto);
        accountsEntity.setAccountNumber(randomAccNumber);
        accountsEntity.setCustomer(customerResults);
        accountsRepository.save(accountsEntity);
    }


    @Override
    public CustomerDto getCustomerByMobileNumber(String mobileNumber)
    {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() ->
                new ResourceNotFoundException("Customer not found for mobile number: " + mobileNumber));
        Accounts accounts = accountsRepository.findByCustomer_CustomerId(customer.getCustomerId());
        if (accounts == null)
        {
            throw new ResourceNotFoundException("Account not found for customer: " + mobileNumber);
        }
        return CustomerMapper.customerAccountEntityToCustomerDto(customer, accounts);
    }

    @Override
    @Transactional
    public void updateAccount(CustomerDto customerDto)
    {
        Customer customer = customerRepository
                .findByMobileNumber(customerDto.getMobileNumber())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found for mobile number: " + customerDto.getMobileNumber()));

        if (customerDto.getAccountsDto() == null) {
            throw new IllegalArgumentException("Account details are required to update an account");
        }

        Accounts accounts = accountsRepository.findByCustomer_CustomerId(customer.getCustomerId());
        if (accounts == null) {
            throw new ResourceNotFoundException("Account not found for customer: " + customerDto.getMobileNumber());
        }
        CustomerMapper.updateCustomerEntity(customerDto, customer);
        AccountMapper.updateAccountEntity(customerDto.getAccountsDto(), accounts);
        customerRepository.save(customer);
        accountsRepository.save(accounts);
    }

    @Override
    @Transactional
    public void deleteAccount(String mobileNumber)
    {
        Customer customer = customerRepository
                .findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found for mobile number: " + mobileNumber));
        Accounts accounts = accountsRepository.findByCustomer_CustomerId(customer.getCustomerId());
        if (accounts != null)
        {
            accountsRepository.delete(accounts);
        }
        customerRepository.delete(customer);
    }

    @Override
    public List<CustomerDto> getAllCustomers()
    {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer customer : customers)
        {
            Accounts accounts = accountsRepository.findByCustomer_CustomerId(customer.getCustomerId());
            if (accounts != null) {
                CustomerDto customerDto = CustomerMapper.customerAccountEntityToCustomerDto(customer, accounts);
                customerDtos.add(customerDto);
            }
        }
        return customerDtos;
    }

    @Override
    public CustomerDto getCustomerById(Long customerId)
    {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
        Accounts accounts = accountsRepository.findByCustomer_CustomerId(customer.getCustomerId());
        if (accounts == null)
        {
            throw new ResourceNotFoundException("Account not found for customer ID: " + customerId);
        }
        return CustomerMapper.customerAccountEntityToCustomerDto(customer, accounts);
    }
}
