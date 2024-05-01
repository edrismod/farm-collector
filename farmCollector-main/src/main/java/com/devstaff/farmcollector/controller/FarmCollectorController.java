package com.devstaff.farmcollector.controller;

import com.devstaff.farmcollector.dao.CropReport;
import com.devstaff.farmcollector.dao.FarmReport;
import com.devstaff.farmcollector.dao.Harvested;
import com.devstaff.farmcollector.dao.Planted;
import com.devstaff.farmcollector.exceptions.NotFoundException;
import com.devstaff.farmcollector.usecases.PlantAndHarvestService;
import com.devstaff.farmcollector.usecases.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FarmCollectorController {
    private final PlantAndHarvestService plantAndHarvestService;
    private final ReportService reportService;

    @PostMapping(value = "/planted",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addPlantedData(@RequestBody Planted planted) {
        plantAndHarvestService.plant(planted);
    }

    @PostMapping(value = "/harvested",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addHarvestedData(@RequestBody Harvested harvested) throws NotFoundException {
        plantAndHarvestService.harvest(harvested);
    }

    @GetMapping(value = "/report/farm/{farmName}" ,produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FarmReport> generateFarmReport(@PathVariable String farmName) throws NotFoundException {
        return ResponseEntity.ok(reportService.getReportByFarm(farmName));
    }

    @GetMapping(value = "/report/crop/{cropType}",produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CropReport> generateCropReport(@PathVariable String cropType) {
        // Implement logic to generate crop report
        return ResponseEntity.ok(reportService.getReportByCrop(cropType));
    }
}
