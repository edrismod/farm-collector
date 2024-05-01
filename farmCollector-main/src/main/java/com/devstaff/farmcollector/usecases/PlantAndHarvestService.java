package com.devstaff.farmcollector.usecases;


import com.devstaff.farmcollector.dao.CropDao;
import com.devstaff.farmcollector.dao.Harvested;
import com.devstaff.farmcollector.dao.Planted;
import com.devstaff.farmcollector.exceptions.NotFoundException;
import com.devstaff.farmcollector.service.CropService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlantAndHarvestService {
    private final CropService cropService;

    public void plant(Planted planted)  {
        cropService.createCrop(CropDao.builder()
                        .plantingArea(planted.getPlantingArea())
                        .cropName(planted.getCropName())
                        .farmName(planted.getFarmName())
                        .expectedProduct(planted.getExpectedProduct())
                .build());
    }

    public void harvest(Harvested harvested) throws NotFoundException {
        cropService.updateCrop(CropDao.builder()
                        .actualProduct(harvested.getActualProduct())
                        .cropName(harvested.getCropName())
                        .farmName(harvested.getFarmName())
                .build());

    }
}
