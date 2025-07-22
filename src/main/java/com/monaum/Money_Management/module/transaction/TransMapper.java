package com.monaum.Money_Management.module.transaction;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransMapper {
    Transaction toEntity(CreateTransactionReqDto dto);
}
