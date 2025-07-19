package com.monaum.Money_Management.module.transaction;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Monaum Hossain
 * @since jul 18, 2025
 */

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction toEntity(CreateTransactionReqDto dto);

    void toEntity(UpdateTransactionReqDto dto, @MappingTarget Transaction transaction);
    TransactionResDto toDto(Transaction transaction);
}



