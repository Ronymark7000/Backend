package com.project.JewelHub.items;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_code")
    private int itemCode;

    @Column (nullable = false, length = 30)
    @NotEmpty
    @Size(min=1, message="Enter the Item Name")
    private String itemName;

    @Column (nullable = false, length = 10)
    @NotBlank (message = "Enter the material of the Item")
    @Pattern ( regexp = "^(Gold|Silver)$", message = "Material is compulsory")
    private String material;

    @Column (nullable = false, length = 20)
    private String category;

    @Column (nullable = false)
    @NotNull(message = "Enter the Karat of the Item")
    private int karat;

    @NotNull
    @DecimalMin(value = "0.1", message = "Gross weight invalid")
    private double grossWeight;

    @NotEmpty
//    @Pattern(regexp = "^\\d+(\\.\\d+)?%$",message = "Percentage should be in 'n%' or 'n.n%'")
    private String wastage;

    @NotNull
    @DecimalMin(value = "0.1", message = "Net weight invalid")
    private double netWeight;

    @NotNull(message = "Gold Price is Required")
    @DecimalMin(value = "0.1", message = "Price must be stored")
    private int goldPrice;

    //Optional Field, So no Annotations required.
    private int costOfStone;

    @NotNull(message = "Manufacture Price is Required")
    @DecimalMin(value = "0.0", message = "Price must be greater than 0.0 or equal")
    private int manufactureCost;

    private String description;

    @NotNull(message = "Final Price is Required")
    @DecimalMin(value = "0.1", message = "Price must be greater than 0.0 or equal")
    private int totalCost;

    @Column(name = "image")
    private String itemImageUrl;

    @Column(name = "video")
    private String itemVideoUrl;

//    public Item( String itemName, String material, int karat, double grossWeight, String wastage, double netWeight, int goldPrice, int costOfStone, int manufactureCost, String description, int totalCost, String itemImageUrl) {
//
//        this.itemName = itemName;
//        this.material = material;
//        this.karat = karat;
//        this.grossWeight = grossWeight;
//        this.wastage = wastage;
//        this.netWeight = netWeight;
//        this.goldPrice = goldPrice;
//        this.costOfStone = costOfStone;
//        this.manufactureCost = manufactureCost;
//        this.description = description;
//        this.totalCost = totalCost;
//        this.itemImageUrl = itemImageUrl;
//    }
}
