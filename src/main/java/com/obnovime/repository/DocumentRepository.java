package com.obnovime.repository;

import com.obnovime.model.DocumentFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentFile, Long>, DocumentRepositoryCustom {

    @Query("SELECT d FROM DocumentFile d ORDER BY d.renewalDate ASC")
    List<DocumentFile> findAllByOrderByRenewalDateAsc();

    Page<DocumentFile> findByArhivaTrue(Pageable pageable);

    Page<DocumentFile> findByArhivaFalse(Pageable pageable);

//    List<DocumentFile> findAllByCreatedByIdOrderByRenewalDateAsc(Long createdById);
}