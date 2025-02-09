package com.obnovime.repository;

import com.obnovime.model.VehicleInspectionPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VehicleInspectionPeriodRepository extends JpaRepository<VehicleInspectionPeriod, Long> {
    Optional<VehicleInspectionPeriod> findByDescription(String description);
    Optional<VehicleInspectionPeriod> findById(Long id);
}
