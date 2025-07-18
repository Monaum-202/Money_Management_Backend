package com.monaum.Money_Management.module.incomes;

import com.monaum.Money_Management.enums.ResponseStatusType;
import com.monaum.Money_Management.model.ResponseBuilder;
import com.monaum.Money_Management.model.SuccessResponse;

import com.monaum.Money_Management.module.user.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    @Autowired private IncomeService incomeService;

    @PostMapping
    public ResponseEntity<SuccessResponse<IncomeResDto>> createIncome(@Valid @RequestBody CreateIncomeReqDto reqDto) {
        IncomeResDto resData = incomeService.createIncome(reqDto);
        return ResponseBuilder.build(ResponseStatusType.CREATE_SUCCESS, resData);
    }

    @PutMapping
    public ResponseEntity<SuccessResponse<IncomeResDto>> updateIncome(@Valid @RequestBody UpdateIncomeReqDto dto) {
        IncomeResDto resData = incomeService.updateIncome(dto);
        return ResponseBuilder.build(ResponseStatusType.UPDATE_SUCCESS, resData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<Void>> deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
        return ResponseBuilder.build(ResponseStatusType.DELETE_SUCCESS, null);
    }

//    @GetMapping
//    public ResponseEntity<SuccessResponse<List<IncomeResDto>>> getAllIncome() {
//        List<IncomeResDto> resData = incomeService.getAllTags();
//        return ResponseBuilder.build(ResponseStatusType.READ_SUCCESS, resData);
//    }

    @GetMapping
    public ResponseEntity<?> getIncomes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<IncomeResDto> result = incomeService.getAllIncomesByUser(pageable);

        return ResponseBuilder.build(ResponseStatusType.READ_SUCCESS, result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<IncomeResDto>> getIncomeById(@PathVariable Long id) {
        IncomeResDto resData = incomeService.getIncomeById(id);
        return ResponseBuilder.build(ResponseStatusType.READ_SUCCESS, resData);
    }
}
