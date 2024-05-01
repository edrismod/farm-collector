package com.devstaff.farmcollector.service;

import com.devstaff.farmcollector.dao.CropDao;
import com.devstaff.farmcollector.entities.Crop;
import com.devstaff.farmcollector.entities.Farm;
import com.devstaff.farmcollector.exceptions.NotFoundException;
import com.devstaff.farmcollector.repositories.CropRepository;
import com.devstaff.farmcollector.repositories.FarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;


@Component
@RequiredArgsConstructor
public class CropService {
    private final FarmRepository farmRepository;
    private final CropRepository cropRepository;

    public void createCrop(CropDao cropDao) {
        final var newFarm = new Farm();
        newFarm.setFarmName(cropDao.getFarmName());

        final var farm = farmRepository.findFarmByFarmName(cropDao.getFarmName())
                .orElse(farmRepository.save(newFarm));

      if(cropRepository.findByCropNameIgnoreCaseAndFarm_FarmNameIgnoreCase(cropDao.getCropName(), cropDao.getFarmName()).isPresent()){
          throw new DuplicateKeyException("Crop for farm exists");
      }

      final var crop = new Crop();
      crop.setCropName(cropDao.getCropName());
      crop.setFarm(farm);
      crop.setPlantingArea(cropDao.getPlantingArea());
      crop.setExpectedProduct(cropDao.getExpectedProduct());
      crop.setSeasonStart(LocalDate.now());
      cropRepository.save(crop);
    }

    public void updateCrop(CropDao cropDao) throws NotFoundException {

        final var crop = cropRepository.findByCropNameIgnoreCaseAndFarm_FarmNameIgnoreCase(cropDao.getCropName(), cropDao.getFarmName())
                .orElseThrow(()->new NotFoundException(cropDao.getCropName()+ " not found"));

        crop.setActualProduct(cropDao.getActualProduct());
        crop.setSeasonEnd(LocalDate.now());
        cropRepository.save(crop);
    }

    public List<Crop> findAllCropsForFarm(String farmName) throws NotFoundException {
        final var farm = farmRepository.findFarmByFarmName(farmName)
                .orElseThrow(()->new NotFoundException(farmName+ " not found"));
        return cropRepository.findCropByFarm(farm);
    }

    public List<Crop> findAllCropsByCropName(String cropName){
        return cropRepository.findCropByCropName(cropName);
    }
}
