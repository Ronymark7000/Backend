package com.project.JewelHub.datascapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/storees")
public class Price {

    /*private final List<GoldData> goldDataList = new ArrayList<>();

    @GetMapping("/scrape")
    public List<GoldData> scrapeGoldPrices() {
        try {
            // Fetch HTML content from the website
            Document doc = Jsoup.connect("https://www.ashesh.com.np/gold/").get();

            // Extract data
            Elements countryElements = doc.select(".country");
            for (Element countryElement : countryElements) {
                String type = countryElement.select(".name").text().trim();
                String price = countryElement.select(".rate_buying").text().trim();
                String unit = countryElement.select(".unit").text().trim();
                goldDataList.add(new GoldData(type, price, unit));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return goldDataList;
    }

    // Scheduled method to scrape gold prices every day at 11:15 AM
    @Scheduled(cron = "0 16 20 * * *") // Cron expression for 11:15 AM every day
    public void scrapeGoldPricesScheduled() {
        scrapeGoldPrices();
    }*/

    static class GoldData {
        private String type;
        private String price;
        private String unit;

        public GoldData(String type, String price, String unit) {
            this.type = type;
            this.price = price;
            this.unit = unit;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}