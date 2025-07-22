package com.monaum.Money_Management.module.transaction;

import com.monaum.Money_Management.enums.TransactionType;
import com.monaum.Money_Management.model.AbstractModel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * Monaum Hossain
 * @since jul 18, 2025
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
@EqualsAndHashCode(callSuper = true)
public class Transaction extends AbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false, length = 10)
    private TransactionType transactionType;

    @Column(name = "source_id", nullable = false)
    private Long source;

    @Column(name = "wallet_id", nullable = false)
    private Long wallet;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "currency", length = 10, nullable = false)
    private String currency;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "date")
    private LocalDate date;

}
