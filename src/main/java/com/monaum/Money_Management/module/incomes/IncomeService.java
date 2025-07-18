package com.monaum.Money_Management.module.incomes;

import com.monaum.Money_Management.config.SecurityUtil;
import com.monaum.Money_Management.exception.CustomException;
import com.monaum.Money_Management.module.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Monaum Hossain
 * @since jul 18, 2025
 */

@Service
@RequiredArgsConstructor
public class IncomeService {

    @Autowired private IncomeRepo incomeRepo;
    @Autowired private IncomeMapper incomeMapper;
    @Autowired private SecurityUtil securityUtil;

    @Transactional
    public IncomeResDto createIncome(CreateIncomeReqDto reqDto) throws CustomException {
        Income income = incomeMapper.toEntity(reqDto);
        income = incomeRepo.save(income);

        return incomeMapper.toDto(income);
    }

    public IncomeResDto getIncomeById(Long id) throws CustomException {
        Income income = incomeRepo.findById(id).orElseThrow(() -> new CustomException("Income not found", HttpStatus.NOT_FOUND));

        return incomeMapper.toDto(income);
    }

    public Page<IncomeResDto> getAllIncomesByUser(Pageable pageable) {
        User currentUser = securityUtil.getCurrentUser().orElseThrow(() -> new CustomException("User not authenticated", HttpStatus.UNAUTHORIZED));

        Page<Income> incomes = incomeRepo.findAllByCreatedBy(currentUser, pageable);
        return incomes.map(incomeMapper::toDto);
    }


    @Transactional
    public IncomeResDto updateIncome(UpdateIncomeReqDto dto) {
        Income income = incomeRepo.getReferenceById(dto.getId());
        incomeMapper.toEntity(dto, income);

        return incomeMapper.toDto(incomeRepo.save(income));
    }

    public void deleteIncome(Long id) throws CustomException {
        Income income = incomeRepo.findById(id).orElseThrow(() -> new CustomException("Income not found", HttpStatus.NOT_FOUND));
        incomeRepo.delete(income);
    }
}
