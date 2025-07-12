package com.monaum.Money_Management.module.incomes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncomeResDto {

    private Long id;
    private String source;
    private String wallet;
    private Double amount;
    private String currency;
    private String description;
    private String date;
}
