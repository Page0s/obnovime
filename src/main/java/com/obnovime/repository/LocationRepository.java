package com.obnovime.repository;

import com.obnovime.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    @SuppressWarnings("null")
    List<Location> findAll();
}
