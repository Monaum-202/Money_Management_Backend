package com.monaum.Money_Management.module.incomes;

import com.monaum.Money_Management.module.user.User;
import jdk.jfr.Registered;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Monaum Hossain
 * @since jul 18, 2025
 */

@Repository
public interface IncomeRepo extends JpaRepository<Income, Long> {

    Page<Income> findAllByCreatedBy(User createdBy, Pageable pageable);
}
