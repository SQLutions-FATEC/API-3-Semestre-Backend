package com.sqlutions.altave.repository;

import com.sqlutions.altave.entity.AnalyticsSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalyticsSummaryRepository extends JpaRepository<AnalyticsSummary, Long> {
    // Pode adicionar filtros por período, sexo, etc. se quiser.
}
