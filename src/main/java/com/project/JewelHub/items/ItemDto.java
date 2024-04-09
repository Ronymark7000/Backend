package com.project.JewelHub.items;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemDto {
    private int itemCode;
    private String itemName;
    private String material;
    private String category;
    private int karat;
    private double grossWeight;
    private String wastage;
    private double netWeight;
    private int goldPrice;
    private int costOfStone;
    private int manufactureCost;
    private String description;
    private int totalCost;
    private String itemImageUrl;
    private String itemVideoUrl;
}


