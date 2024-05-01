package com.devstaff.farmcollector.service;

import com.devstaff.farmcollector.entities.Farm;
import com.devstaff.farmcollector.exceptions.NotFoundException;
import com.devstaff.farmcollector.repositories.FarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FarmService {
    private final FarmRepository farmRepository;

    public void createFarm(String farmName){
        if(farmRepository.findFarmByFarmName(farmName).isPresent()){
            throw new DuplicateKeyException("farmName exists");
        }

        final var farm = new Farm();
        farm.setFarmName(farmName);
        farmRepository.save(farm);
    }

    public Farm findFarmByName(String farmName) throws NotFoundException {
        return farmRepository.findFarmByFarmName(farmName).orElseThrow(()-> new NotFoundException( farmName +" not Found"));
    }
}
