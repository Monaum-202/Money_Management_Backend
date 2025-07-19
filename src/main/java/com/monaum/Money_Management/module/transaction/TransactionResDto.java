package com.monaum.Money_Management.module.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Monaum Hossain
 * @since jul 18, 2025
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResDto {

    private Long id;
    private String transactionType;
    private String source;
    private String wallet;
    private Double amount;
    private String currency;
    private String description;
    private String date;
}
