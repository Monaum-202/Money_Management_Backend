package com.monaum.Money_Management.module.transaction;

import com.monaum.Money_Management.enums.TransactionType;
import com.monaum.Money_Management.model.AbstractModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Monaum Hossain
 * @since jul 18, 2025
 */

@Getter
@Setter
@Entity
@Builder
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
    private TransactionType type;

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
