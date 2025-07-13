package com.monaum.Money_Management.module.incomes;

import com.monaum.Money_Management.model.AbstractModel;
import com.monaum.Money_Management.module.sources.Source;
import com.monaum.Money_Management.module.wallets.Wallet;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "incomes")
@EqualsAndHashCode(callSuper = true)
public class Income extends AbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "currency", length = 10, nullable = false)
    private String currency;

    @Column(name = "description", length = 255, nullable = true)
    private String description;

    @Column(name = "date")
    private String date;

}
