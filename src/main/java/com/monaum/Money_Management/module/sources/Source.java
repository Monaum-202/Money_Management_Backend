package com.monaum.Money_Management.module.sources;

import com.monaum.Money_Management.model.AbstractModel;
import jakarta.persistence.*;
import lombok.*;

/**
 * Monaum Hossain
 * @since jul 18, 2025
 */

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "sources")
public class Source extends AbstractModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "icon", length = 50)
    private String icon;

    @Column(name = "color", length = 20)
    private String color;

}
