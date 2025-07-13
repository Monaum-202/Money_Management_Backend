package com.monaum.Money_Management.module.incomes;

import com.monaum.Money_Management.module.user.User;
import jdk.jfr.Registered;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface IncomeRepo extends JpaRepository<Income, Long> {

    Page<Income> findAllByCreatedBy(User createdBy, Pageable pageable);
}
