package com.devstaff.farmcollector.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class CropReport {
    final String cropName;
    final List<Farm> farmReport;
    @RequiredArgsConstructor
    @Getter
    public static class Farm {
        final double plantingArea;
        final double expectedProduct;
        final double actualProduct;
        final String farmName;
        final LocalDate seasonStart;
        final LocalDate seasonEnd;
    }

}
