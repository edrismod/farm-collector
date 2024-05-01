package com.devstaff.farmcollector.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class FarmReport {
    final String farmName;
    final List<Crop> cropReport;

    @RequiredArgsConstructor
    @Getter
    public static class Crop{
        final String cropName;
        final double plantingArea;
        final double expectedProduct;
        final double actualProduct;
        final LocalDate seasonStart;
        final LocalDate seasonEnd;
    }
}
