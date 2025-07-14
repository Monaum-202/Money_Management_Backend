package com.monaum.Money_Management.module.incomes;

import com.monaum.Money_Management.annotations.ExistsInDatabase;
import com.monaum.Money_Management.module.sources.Source;
import com.monaum.Money_Management.module.wallets.Wallet;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateIncomeReqDto {

    @NotNull(message = "Income ID is required.")
    private Long id;

    @NotNull(message = "Source ID is required.")
    @ExistsInDatabase(entity = Source.class, message = "Source not found")
    private Long sourceId;

    @NotNull(message = "Wallet ID is required.")
    @ExistsInDatabase(entity = Wallet.class, message = "Wallet not found")
    private Long walletId;

    @NotNull(message = "Amount is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0.")
    private Double amount;

    @NotBlank(message = "Currency is required.")
    @Size(max = 10, message = "Currency code must be at most 10 characters.")
    private String currency;

    @Size(max = 255, message = "Description can't exceed 255 characters.")
    private String description;

    @NotBlank(message = "Date is required.")
    private String date; // Consider using LocalDate for better date validation
}
