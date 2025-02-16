package com.obnovime.repository;

import com.obnovime.model.DocumentFile;
import com.obnovime.model.DocumentType;
import com.obnovime.model.ResourceType;
import com.obnovime.model.Location;
import com.obnovime.model.StatusOption;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class DocumentRepositoryCustomImpl implements DocumentRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<DocumentFile> searchDocuments(
          List<String> docTypes,
          List<String> resourceTypes,
          List<String> statusOptions,
          List<String> locationNames,
          LocalDate dateFrom,
          LocalDate dateTo
  ) {
    // 1) Kreiramo CriteriaBuilder i CriteriaQuery
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<DocumentFile> criteriaQuery = criteriaBuilder.createQuery(DocumentFile.class);

    // 2) Root za DocumentFile entitet
    Root<DocumentFile> documentRoot = criteriaQuery.from(DocumentFile.class);

    // 3) JOIN (LEFT) na vezane entitete (omogućava da DocumentFile postoji i bez tih entiteta)
    Join<DocumentFile, DocumentType> dtJoin = documentRoot.join("documentType", JoinType.LEFT);
    Join<DocumentFile, ResourceType> rtJoin = documentRoot.join("resourceType", JoinType.LEFT);
    Join<DocumentFile, Location> locJoin = documentRoot.join("location", JoinType.LEFT);
    Join<DocumentFile, StatusOption> stJoin = documentRoot.join("status", JoinType.LEFT);

    // 4) Inicijaliziramo listu AND uvjeta
    List<Predicate> andPredicates = new ArrayList<>();

    // 4a) Filtriraj po docTypes (ako postoji)
    if (docTypes != null && !docTypes.isEmpty()) {
      andPredicates.add(dtJoin.get("name").in(docTypes));
    }

    // 4b) Filtriraj po resourceTypes (ako postoji)
    if (resourceTypes != null && !resourceTypes.isEmpty()) {
      andPredicates.add(rtJoin.get("name").in(resourceTypes));
    }

    // 4c) Filtriraj po statusOptions (ako postoji)
    if (statusOptions != null && !statusOptions.isEmpty()) {
      andPredicates.add(stJoin.get("name").in(statusOptions));
    }

    // 4d) Filtriraj po locationNames, uz OR (lokacija iz liste ILI lokacija null),
    //     koji se zatim AND-ira s ostalim uvjetima
    if (locationNames != null && !locationNames.isEmpty()) {
      Predicate inLocations = locJoin.get("name").in(locationNames);
      Predicate nullLocation = criteriaBuilder.isNull(locJoin.get("id"));
      andPredicates.add(criteriaBuilder.or(inLocations, nullLocation));
    }

    // 5) Filtri po datumima (AND logika)
    //    dateFrom => renewalDate >= dateFrom
    if (dateFrom != null) {
      andPredicates.add(criteriaBuilder.greaterThanOrEqualTo(documentRoot.get("renewalDate"), dateFrom));
    }

    //    dateTo => renewalDate <= dateTo
    if (dateTo != null) {
      andPredicates.add(criteriaBuilder.lessThanOrEqualTo(documentRoot.get("renewalDate"), dateTo));
    }

    // 6) Ako postoje uvjeti, sve ih spajamo s AND
    if (!andPredicates.isEmpty()) {
      criteriaQuery.where(criteriaBuilder.and(andPredicates.toArray(new Predicate[0])));
    }

    // 7) Sortiramo rezultate po renewalDate (uzlazno)
    criteriaQuery.orderBy(criteriaBuilder.asc(documentRoot.get("renewalDate")));

    // 8) Vraćamo rezultate upita
    return entityManager.createQuery(criteriaQuery).getResultList();
  }
}
