package com.project.JewelHub.datascapper;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MetalPriceService {

    private final MetalPriceRepo metalPriceRepo;

    @Scheduled(cron = "0 07 12 * * *")// Scheduled for 11:15 AM every day
    public void scrapeAndSaveMetalPrices() {
        System.out.println("Successfully got Gold & Silver Price at 11:15 AM ");
        List<Price.GoldData> scrapedData = scrapeGoldPrices();

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

        metalPriceRepo.save(metalPrice);
    }

    private List<Price.GoldData> scrapeGoldPrices() {
        List<Price.GoldData> goldDataList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://www.ashesh.com.np/gold/").get();
            Elements countryElements = doc.select(".country");
            for (Element countryElement : countryElements) {
                String type = countryElement.select(".name").text().trim();
                String price = countryElement.select(".rate_buying").text().trim();
                String unit = countryElement.select(".unit").text().trim();
                goldDataList.add(new Price.GoldData(type, price, unit));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return goldDataList;
    }
}
