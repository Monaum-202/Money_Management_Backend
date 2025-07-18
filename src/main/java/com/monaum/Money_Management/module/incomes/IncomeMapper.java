package com.monaum.Money_Management.module.incomes;

import com.monaum.Money_Management.exception.CustomException;
import com.monaum.Money_Management.module.sources.Source;
import com.monaum.Money_Management.module.sources.SourceRepo;
import com.monaum.Money_Management.module.wallets.Wallet;
import com.monaum.Money_Management.module.wallets.WalletRepo;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface IncomeMapper {
    Income toEntity(CreateIncomeReqDto dto);
    void toEntity(UpdateIncomeReqDto dto, @MappingTarget Income income);
    IncomeResDto toDto(Income income);
}



