package com.project.JewelHub.datascapper;

import com.project.JewelHub.util.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/store")
public class MetalPriceController {

    @Autowired
    private MetalPriceRepo metalPriceRepo;

    @GetMapping("/metal-prices")
    public ResponseEntity<ResponseWrapper<List<MetalPrice>>> getMetalPrices() {
        List<MetalPrice> metalPrices = metalPriceRepo.findAll();
        ResponseWrapper<List<MetalPrice>> response = new ResponseWrapper<>(true, 200, "Metal prices retrieved successfully", metalPrices);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/currentMetalPrice")
    public ResponseEntity<ResponseWrapper<MetalPrice>> getLatestMetalPrice() {
        Optional<MetalPrice> latestMetalPriceOptional = metalPriceRepo.findLatestMetalPrice();
        if (latestMetalPriceOptional.isPresent()) {
            MetalPrice latestMetalPrice = latestMetalPriceOptional.get();
            ResponseWrapper<MetalPrice> response = new ResponseWrapper<>(true, 200, "Latest metal price retrieved successfully", latestMetalPrice);
            return ResponseEntity.ok(response);
        } else {
            ResponseWrapper<MetalPrice> response = new ResponseWrapper<>(false, 404, "No metal prices found", null);
            return ResponseEntity.status(404).body(response);
        }
    }


}
