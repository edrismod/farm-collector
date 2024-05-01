package com.devstaff.farmcollector.dao;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CropDao {
    String cropName;
    double plantingArea;
    double expectedProduct;
    double actualProduct;
    String farmName;
}
