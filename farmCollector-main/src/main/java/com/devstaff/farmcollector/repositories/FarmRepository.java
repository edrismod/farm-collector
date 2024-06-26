package com.devstaff.farmcollector.repositories;

import com.devstaff.farmcollector.entities.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FarmRepository extends JpaRepository<Farm,Long> {
    Optional<Farm> findFarmByFarmName(String farmName);
}
