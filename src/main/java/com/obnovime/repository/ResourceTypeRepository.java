package com.obnovime.repository;

import com.obnovime.model.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceTypeRepository extends JpaRepository<ResourceType, Long> {
    @SuppressWarnings("null")
    List<ResourceType> findAll();
    
    Optional<ResourceType> findByName(String name);
}
