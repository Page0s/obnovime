package com.obnovime.repository;

import com.obnovime.model.RenewalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RenewalHistoryRepository extends JpaRepository<RenewalHistory, Long> {
    List<RenewalHistory> findByDocumentFileId(Long id);
}