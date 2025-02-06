package com.obnovime.repository;

import com.obnovime.model.DocumentFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentFile, Long> {

    @Query("SELECT d FROM DocumentFile d ORDER BY d.renewalDate ASC")
    List<DocumentFile> findAllByOrderByRenewalDateAsc();
}