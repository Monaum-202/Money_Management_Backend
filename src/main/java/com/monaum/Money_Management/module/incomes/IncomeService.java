package com.monaum.Money_Management.module.incomes;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncomeService {

    @Autowired private IncomeRepo incomeRepo;
    @Autowired private IncomeMapper incomeMapper;

    public Income createIncome(CreateIncomeReqDto dto) {
        Income income = incomeMapper.toEntity(dto);
        return incomeRepo.save(income);
    }

//    Income income = incomeRepo.findById(id).orElseThrow();
//    IncomeResDto resDto = incomeMapper.toDto(income);
//
//    List<Income> incomes = incomeRepo.findAllByUser(user);
//    List<IncomeResDto> responseList = incomeMapper.toDtoList(incomes);

}
