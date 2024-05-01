package com.devstaff.farmcollector.usecases;

import com.devstaff.farmcollector.dao.CropReport;
import com.devstaff.farmcollector.dao.FarmReport;
import com.devstaff.farmcollector.entities.Crop;
import com.devstaff.farmcollector.exceptions.NotFoundException;
import com.devstaff.farmcollector.service.CropService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportService {
    private final CropService cropService;

    public FarmReport getReportByFarm(String farmName) throws NotFoundException {
        final var crops = cropService.findAllCropsForFarm(farmName);

        List<FarmReport.Crop> cropReports = crops.stream()
                .map(this::mapToCropReport)
                .toList();

        return new FarmReport(farmName, cropReports);
    }

    private FarmReport.Crop mapToCropReport(Crop crop) {
        return new FarmReport.Crop(crop.getCropName(), crop.getPlantingArea(), crop.getExpectedProduct(), crop.getActualProduct(),crop.getSeasonStart(),crop.getSeasonEnd());
    }

    public CropReport getReportByCrop(String cropName){
        final var crops = cropService.findAllCropsByCropName(cropName);
        final var reports = crops.stream().map(this::mapToFarmReport)
                .toList();
        return new CropReport(cropName, reports);
    }

    private CropReport.Farm mapToFarmReport(Crop crop){
        return new CropReport.Farm(crop.getPlantingArea(),crop.getExpectedProduct(),crop.getActualProduct(),crop.getFarm().getFarmName(),crop.getSeasonStart(),crop.getSeasonEnd());
    }

}
