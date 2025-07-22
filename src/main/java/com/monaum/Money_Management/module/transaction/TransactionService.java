package com.monaum.Money_Management.module.transaction;

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
public class TransactionService {

    @Autowired private TransactionRepo transactionRepo;
    @Autowired private TransactionMapper transactionMapper;
    @Autowired private SecurityUtil securityUtil;

    @Transactional
    public TransactionResDto create(CreateTransactionReqDto reqDto) {
            Transaction transaction = transactionMapper.toEntity(reqDto);
            transaction = transactionRepo.save(transaction);

            Transaction sss = new Transaction();
            sss.setTransactionType(transaction.getTransactionType());

            return transactionMapper.toDto(transaction);
    }

    public TransactionResDto getById(Long id) throws CustomException {
        Transaction transaction = transactionRepo.findById(id).orElseThrow(() -> new CustomException("Transaction not found", HttpStatus.NOT_FOUND));

        return transactionMapper.toDto(transaction);
    }

    public Page<TransactionResDto> getAllByUser(Pageable pageable) {
        User currentUser = securityUtil.getCurrentUser().orElseThrow(() -> new CustomException("User not authenticated", HttpStatus.UNAUTHORIZED));

        Page<Transaction> transactions = transactionRepo.findAllByCreatedBy(currentUser, pageable);
        return transactions.map(transactionMapper::toDto);
    }

    @Transactional
    public TransactionResDto update(UpdateTransactionReqDto dto) {
        Transaction transaction = transactionRepo.getReferenceById(dto.getId());

        transactionMapper.toEntity(dto, transaction);

        Transaction updated = transactionRepo.save(transaction);

        return transactionMapper.toDto(updated);
    }

    
    public void delete(Long id) throws CustomException {
        Transaction transaction = transactionRepo.findById(id).orElseThrow(() -> new CustomException("Transaction not found", HttpStatus.NOT_FOUND));
        transactionRepo.delete(transaction);
    }
}
