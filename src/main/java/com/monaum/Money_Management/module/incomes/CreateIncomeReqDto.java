package com.monaum.Money_Management.module.incomes;

import com.monaum.Money_Management.module.sources.Source;
import com.monaum.Money_Management.module.sources.SourceRepo;
import com.monaum.Money_Management.module.wallets.Wallet;
import com.monaum.Money_Management.module.wallets.WalletRepo;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateIncomeReqDto {


    @NotBlank(message = "Source required.")
    private Long source;

    @NotBlank(message = "Wallet required.")
    private Long wallet;

    @NotBlank(message = "Amount required.")
    private Double amount;

    @NotBlank(message = "Currency required.")
    private String currency;

    @NotBlank(message = "Description required.")
    private String description;

    private String date;

}
