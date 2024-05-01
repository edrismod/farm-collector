package com.devstaff.farmcollector.repositories;

import com.devstaff.farmcollector.entities.Crop;
import com.devstaff.farmcollector.entities.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CropRepository extends JpaRepository<Crop,Long> {

    @Query("select c from Crop c where upper(c.cropName) = upper(?1) and upper(c.farm.farmName) = upper(?2)")
    Optional<Crop> findByCropNameIgnoreCaseAndFarm_FarmNameIgnoreCase(String cropName, String farmName);

    List<Crop> findCropByFarm(Farm farm);

    List<Crop> findCropByCropName(String cropName);
}
