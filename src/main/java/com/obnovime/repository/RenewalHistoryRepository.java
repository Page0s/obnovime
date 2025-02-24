package com.obnovime.repository;

import com.obnovime.model.RenewalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RenewalHistoryRepository extends JpaRepository<RenewalHistory, Long> {
}