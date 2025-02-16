package com.obnovime.repository;

import com.obnovime.model.DocumentFile;

import java.time.LocalDate;
import java.util.List;

public interface DocumentRepositoryCustom {

  List<DocumentFile> searchDocuments(
          List<String> docTypes,
          List<String> resourceTypes,
          List<String> statusOptions,
          List<String> locationNames,
          LocalDate dateFrom,
          LocalDate dateTo
  );
}
