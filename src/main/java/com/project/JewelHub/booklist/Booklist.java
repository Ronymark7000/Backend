package com.project.JewelHub.booklist;
import com.project.JewelHub.items.Item;
import com.project.JewelHub.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name= "booklist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booklistId")
    private long bookListID;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_code", nullable = false)
    private Item item;
}