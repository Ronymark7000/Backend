package com.project.JewelHub.booklist;

import com.project.JewelHub.items.Item;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BooklistDto {
    private long bookListID;
    private Item item;
}
