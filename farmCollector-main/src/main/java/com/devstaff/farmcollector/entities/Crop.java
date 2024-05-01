package com.devstaff.farmcollector.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
public class Crop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Setter
    @Getter
    @Column(unique = true)
    private String cropName;
    @Setter
    @Getter
    private double plantingArea;
    @Setter
    @Getter
    private double expectedProduct;
    @Setter
    @Getter
    private double actualProduct;
    @Getter
    @ManyToOne
    @JoinColumn(name = "farm_id", nullable = false,updatable = false)
    private Farm farm;
    @Getter
    @Setter
    private LocalDate seasonStart;
    @Getter @Setter
    private LocalDate seasonEnd;


    public void setFarm(Farm farm) {
        this.farm = farm;
    }

}
