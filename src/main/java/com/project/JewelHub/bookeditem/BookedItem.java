package com.project.JewelHub.bookeditem;

import com.project.JewelHub.booklist.Booklist;
import com.project.JewelHub.items.Item;
import com.project.JewelHub.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="booked_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookedItem {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name="bookedItemId")
    private long bookedItemId;

    @ManyToOne
    @JoinColumn(name="userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="itemCode", nullable = false)
    private Item item;

    @Column(name = "original_price")
    private double price;

    @Column(name="status")
    private String status;

    @Column(name = "bookedDate")
    private LocalDate bookedDate;

    public BookedItem (Booklist booklist){
        this.user = booklist.getUser();
        this.item = booklist.getItem();
        this.price = booklist.getItem().getTotalCost();
        this.status = "Booking Pending";
        this.bookedDate = LocalDate.now();
    }
}


