package com.monaum.Money_Management.module.sources;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Monaum Hossain
 * @since jul 18, 2025
 */

@Repository
public interface SourceRepo extends JpaRepository<Source, Long> {
}
