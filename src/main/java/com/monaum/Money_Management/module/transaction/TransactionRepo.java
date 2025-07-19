package com.monaum.Money_Management.module.transaction;

import com.monaum.Money_Management.module.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Monaum Hossain
 * @since jul 18, 2025
 */

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    Page<Transaction> findAllByCreatedBy(User createdBy, Pageable pageable);
}
