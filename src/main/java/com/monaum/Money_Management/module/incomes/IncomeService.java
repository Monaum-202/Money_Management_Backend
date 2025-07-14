package com.monaum.Money_Management.module.incomes;

import com.monaum.Money_Management.exception.CustomException;
import com.monaum.Money_Management.model.BaseService;
import com.monaum.Money_Management.module.sources.Source;
import com.monaum.Money_Management.module.sources.SourceRepo;
import com.monaum.Money_Management.module.user.User;
import com.monaum.Money_Management.module.user.UserRepo;
import com.monaum.Money_Management.module.wallets.Wallet;
import com.monaum.Money_Management.module.wallets.WalletRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeService extends BaseService {

    @Autowired private IncomeRepo incomeRepo;
    @Autowired private IncomeMapper incomeMapper;

    @Transactional
    public IncomeResDto createIncome(CreateIncomeReqDto reqDto) throws CustomException {

        Income income = incomeMapper.toEntity(reqDto);
        income.setCreatedBy(super.getAuthenticatedUser());
        income.setUpdatedBy(super.getAuthenticatedUser());
        income = incomeRepo.save(income);

        return incomeMapper.toDto(income);
    }

    public IncomeResDto getIncomeById(Long id) throws CustomException {
        Income income = incomeRepo.findById(id).orElseThrow(() -> new CustomException("Income not found", HttpStatus.NOT_FOUND));

        return incomeMapper.toDto(income);
    }

    public Page<IncomeResDto> getAllIncomesByUser(Pageable pageable) {
        Page<Income> incomes = incomeRepo.findAllByCreatedBy(super.getAuthenticatedUser(), pageable);
        return incomes.map(incomeMapper::toDto);
    }

    @Transactional
    public IncomeResDto updateIncome(UpdateIncomeReqDto dto) {
        Income income = incomeRepo.getReferenceById(dto.getId());

        incomeMapper.updateEntityFromDto(dto, income);
        income.setUpdatedBy(super.getAuthenticatedUser());

        return incomeMapper.toDto(incomeRepo.save(income));
    }

}
