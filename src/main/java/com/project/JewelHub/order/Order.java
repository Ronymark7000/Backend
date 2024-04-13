package com.project.JewelHub.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    private LocalDate orderDate;

    @NotEmpty
    private String customerName;

    @Column(nullable = false, length = 10)
    private long contact;

    private String orderName;

    @Column(nullable = false, length = 255)
    @NotEmpty
    private String description;

    @NotNull
    @DecimalMin(value = "0.1", message = "Estimated weight invalid")
    private double estimatedWeight;

    private int advancePayment;

    private LocalDate deliveryDate;

    private boolean orderStatus;
}
