package com.obnovime.repository;

import com.obnovime.model.DokumentFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<DokumentFile, Long> {

    @Query("SELECT d FROM DokumentFile d ORDER BY d.renewalDate ASC")
    List<DokumentFile> findAllByOrderByRenewalDateAsc();
}