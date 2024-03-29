package com.project.JewelHub.datascapper;

import com.project.JewelHub.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/store")
public class MetalPriceController {

    @Autowired
    private MetalPriceRepo metalPriceRepo;

    @Autowired
    private MetalPriceService metalPriceService;

    @GetMapping("/scrape")
    public ResponseEntity<MetalPrice> scrapeAndSaveMetalPrices() {
        List<Price.GoldData> scrapedData = metalPriceService.scrapeGoldPrices();

        String goldTolaPrice = "";
        String gold10gmPrice = "";
        String silverTolaPrice = "";
        String silver10gmPrice = "";

        for (Price.GoldData data : scrapedData) {
            if (data.getType().equals("Gold Hallmark")) {
                if (data.getUnit().equals("Tola")) {
                    goldTolaPrice = data.getPrice();
                } else if (data.getUnit().equals("10 gram")) {
                    gold10gmPrice = data.getPrice();
                }
            } else if (data.getType().equals("Silver")) {
                if (data.getUnit().equals("Tola")) {
                    silverTolaPrice = data.getPrice();
                } else if (data.getUnit().equals("10 gram")) {
                    silver10gmPrice = data.getPrice();
                }
            }
        }

        MetalPrice metalPrice = new MetalPrice();
        metalPrice.setGoldTola(goldTolaPrice);
        metalPrice.setGold10gm(gold10gmPrice);
        metalPrice.setSilverTola(silverTolaPrice);
        metalPrice.setSilver10gm(silver10gmPrice);
        metalPrice.setPriceDate(LocalDate.now());

        MetalPrice savedMetalPrice = metalPriceRepo.save(metalPrice);

        if (savedMetalPrice != null) {
            return ResponseEntity.ok(savedMetalPrice);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
