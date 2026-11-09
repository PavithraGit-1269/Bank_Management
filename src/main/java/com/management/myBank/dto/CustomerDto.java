package com.management.myBank.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    private Long customerId;

    @NotBlank(message = "Name is required")
    @Size(min = 4,max = 20,message = "Name must be contain 4 to 20 characters")
    private String name;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must contain exactly 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    private String email;

    @NotNull(message = "Account details are required")
    @Valid
    AccountDto accountsDto;
}
