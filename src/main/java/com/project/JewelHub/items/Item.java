package com.project.JewelHub.items;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
public class Item {
    @Id
    private int itemCode;

    @Column (nullable = false, length = 30)
    @NotEmpty
    @Size(min=1, message="Enter the Item Name")
    private String itemName;

    @Column (nullable = false, length = 10)
    @NotBlank (message = "Enter the material of the Item")
    @Pattern ( regexp = "^(Gold|Silver)$", message = "Material is compulsory")
    private String material;

    @Column (nullable = false, length = 5)
    @NotEmpty
    @NotBlank (message = "Enter the Karat of the Item")
    private int karat;

    @NotEmpty
    @DecimalMin(value = "0.1", message = "Gross weight invalid")
    private double grossWeight;

    @NotEmpty
    @Pattern(regexp = "^\\d+(\\.\\d+)?%$",message = "Percentage should be in 'n%' or 'n.n%'")
    private String wastage;

    @NotEmpty
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
}
