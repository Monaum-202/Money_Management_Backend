package com.monaum.Money_Management.module.incomes;

import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface IncomeRepo extends JpaRepository<Income, Long> {
}
