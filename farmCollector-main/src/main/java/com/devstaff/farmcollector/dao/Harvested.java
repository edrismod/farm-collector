package com.devstaff.farmcollector.dao;

import lombok.Value;

@Value
public class Harvested {
    String cropName;
    double actualProduct;
    String farmName;
}
