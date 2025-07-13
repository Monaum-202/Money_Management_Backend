package com.monaum.Money_Management.module.incomes;

import com.monaum.Money_Management.enums.ResponseStatusType;
import com.monaum.Money_Management.model.ResponseBuilder;
import com.monaum.Money_Management.model.SuccessResponse;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping
    public ResponseEntity<SuccessResponse<List<IncomeResDto>>> getAllIncome() {
        List<IncomeResDto> resData = incomeService.getAllTags();
        return ResponseBuilder.build(ResponseStatusType.READ_SUCCESS, resData);
    }
}
