package com.devstaff.farmcollector.dao;

import lombok.Value;

@Value
public class Planted {
    String cropName;
    double plantingArea;
    double expectedProduct;
    String farmName;
}
