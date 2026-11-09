package com.management.myBank.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private Long customerId;

    private Long accountNumber;

    @NotBlank(message = "Account type is required")
    @Pattern(regexp = "Savings|Current", message = "Account type must be Savings or Current")
    private String accountType;

    @NotBlank(message = "Branch address is required")
    @Size(min = 2, max = 150, message = "Branch address must contain 2 to 150 characters")
    private String branchAddress;

}
