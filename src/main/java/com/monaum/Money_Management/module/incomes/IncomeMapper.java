package com.monaum.Money_Management.module.incomes;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Monaum Hossain
 * @since jul 18, 2025
 */

@Mapper(componentModel = "spring")
public interface IncomeMapper {
    Income toEntity(CreateIncomeReqDto dto);
    void toEntity(UpdateIncomeReqDto dto, @MappingTarget Income income);
    IncomeResDto toDto(Income income);
}



