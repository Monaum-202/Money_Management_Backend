package com.monaum.Money_Management.module.incomes;

import com.monaum.Money_Management.exception.CustomException;
import com.monaum.Money_Management.module.sources.Source;
import com.monaum.Money_Management.module.sources.SourceRepo;
import com.monaum.Money_Management.module.wallets.Wallet;
import com.monaum.Money_Management.module.wallets.WalletRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class IncomeMapper {

    @Autowired private WalletRepo walletRepository;
    @Autowired private SourceRepo sourceRepository;

    public Income toEntity(CreateIncomeReqDto dto) {
        Wallet wallet = walletRepository.findById(dto.getWallet())
                .orElseThrow(() -> new CustomException("Wallet not found", HttpStatus.NOT_FOUND));

        Source source = sourceRepository.findById(dto.getSource())
                .orElseThrow(() -> new CustomException("Source not found", HttpStatus.NOT_FOUND));

        return Income.builder()
                .wallet(wallet)
                .source(source)
                .amount(dto.getAmount())
                .currency(dto.getCurrency())
                .description(dto.getDescription())
                .date(dto.getDate())
                .build();
    }

    public IncomeResDto toDto(Income income) {
        return IncomeResDto.builder()
                .id(income.getId())
                .source(income.getSource().getName())
                .wallet(income.getWallet().getName())
                .amount(income.getAmount())
                .currency(income.getCurrency())
                .description(income.getDescription())
                .date(income.getDate())
                .build();
    }

    public List<IncomeResDto> toDtoList(List<Income> incomes) {
        return incomes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
