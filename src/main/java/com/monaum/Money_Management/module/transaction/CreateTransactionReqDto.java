package com.monaum.Money_Management.module.transaction;

import com.monaum.Money_Management.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Monaum Hossain
 * @since jul 18, 2025
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionReqDto {

    @NotBlank(message = "Transaction type required.")
    private TransactionType transactionType;

    @NotNull(message = "Source required.")
    private Long source;

    @NotNull(message = "Wallet required.")
    private Long wallet;

    @NotNull(message = "Amount is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0.")
    private Double amount;

    @NotBlank(message = "Currency required.")
    private String currency;

    @NotBlank(message = "Description required.")
    private String description;

    private LocalDate date;

}
