package com.monaum.Money_Management.module.transaction;

import com.monaum.Money_Management.annotations.RestApiController;
import com.monaum.Money_Management.enums.ResponseStatusType;
import com.monaum.Money_Management.model.ResponseBuilder;
import com.monaum.Money_Management.model.SuccessResponse;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Monaum Hossain
 * @since jul 18, 2025
 */

@RestApiController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<?> getTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<TransactionResDto> result = transactionService.getAllByUser(pageable);

        return ResponseBuilder.build(ResponseStatusType.READ_SUCCESS, result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<TransactionResDto>> getTransactionById(@PathVariable Long id) {
        TransactionResDto resData = transactionService.getById(id);
        return ResponseBuilder.build(ResponseStatusType.READ_SUCCESS, resData);
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<TransactionResDto>> create(@Valid @RequestBody CreateTransactionReqDto reqDto) {
        TransactionResDto resData = transactionService.create(reqDto);
        return ResponseBuilder.build(ResponseStatusType.CREATE_SUCCESS, resData);
    }

    @PutMapping
    public ResponseEntity<SuccessResponse<TransactionResDto>> update(@Valid @RequestBody UpdateTransactionReqDto dto) {
        TransactionResDto resData = transactionService.update(dto);
        return ResponseBuilder.build(ResponseStatusType.UPDATE_SUCCESS, resData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<Void>> delete(@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseBuilder.build(ResponseStatusType.DELETE_SUCCESS, null);
    }

//    @GetMapping
//    public ResponseEntity<SuccessResponse<List<TransactionResDto>>> getAllTransaction() {
//        List<TransactionResDto> resData = transactionService.getAllTags();
//        return ResponseBuilder.build(ResponseStatusType.READ_SUCCESS, resData);
//    }
}
