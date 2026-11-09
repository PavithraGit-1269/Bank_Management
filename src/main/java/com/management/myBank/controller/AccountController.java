package com.management.myBank.controller;

import com.management.myBank.constants.AccountConstants;
import com.management.myBank.dto.CustomerDto;
import com.management.myBank.dto.ResponseDto;
import com.management.myBank.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/accounts/api")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        accountService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstants.ACCOUNT_CREATED, AccountConstants.ACCOUNT_CREATED_MESSAGE));
    }

    @GetMapping("/customer/{mobileNumber}")
    public ResponseEntity<CustomerDto> getCustomerByMobileNumber(
            @PathVariable String mobileNumber) {

        CustomerDto customerModel =
                accountService.getCustomerByMobileNumber(mobileNumber);

        return ResponseEntity.ok(customerModel);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> customers = accountService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/customer/id/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerById(
            @PathVariable Long customerId) {

        CustomerDto customerModel =
                accountService.getCustomerById(customerId);

        return ResponseEntity.ok(customerModel);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateAccount(@Valid @RequestBody CustomerDto customerModel) {
        accountService.updateAccount(customerModel);
        return ResponseEntity.ok("Account updated successfully");
    }

    @DeleteMapping("/delete/{mobileNumber}")
    public ResponseEntity<String> deleteAccount(
            @PathVariable String mobileNumber) {
        accountService.deleteAccount(mobileNumber);
        return ResponseEntity.ok("Account deleted successfully");
    }
}





