package com.obnovime.repository;

import com.obnovime.model.ResponsiblePerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsiblePersonRepository extends JpaRepository<ResponsiblePerson, Long> {
    // You can add custom query methods here if needed
}
