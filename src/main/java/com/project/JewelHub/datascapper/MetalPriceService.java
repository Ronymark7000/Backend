package com.project.JewelHub.datascapper;

import lombok.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MetalPriceService {

    @Scheduled(cron = "0 15 11 * * *") // Scheduled for 11:15 AM every day
    public List<Price.GoldData> scrapeGoldPrices() {
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

