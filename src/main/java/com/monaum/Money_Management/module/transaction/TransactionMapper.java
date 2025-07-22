package com.monaum.Money_Management.module.transaction;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TransactionMapper {


    Transaction toEntity(CreateTransactionReqDto dto);

    void toEntity(UpdateTransactionReqDto dto, @MappingTarget Transaction transaction);

    TransactionResDto toDto(Transaction transaction);
}
