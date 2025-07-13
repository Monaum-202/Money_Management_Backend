package com.monaum.Money_Management.module.incomes;

import com.monaum.Money_Management.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncomeService {

    @Autowired private IncomeRepo incomeRepo;
    @Autowired private IncomeMapper incomeMapper;

    @Transactional
    public IncomeResDto createIncome(CreateIncomeReqDto reqDto) throws CustomException {

        Income income = incomeMapper.toEntity(reqDto);
        income = incomeRepo.save(income);

        return new IncomeResDto(income);
    }




//    Income income = incomeRepo.findById(id).orElseThrow();
//    IncomeResDto resDto = incomeMapper.toDto(income);
//
//    List<Income> incomes = incomeRepo.findAllByUser(user);
//    List<IncomeResDto> responseList = incomeMapper.toDtoList(incomes);

}
