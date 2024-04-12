package com.project.JewelHub.bookeditem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookedItemRepo extends JpaRepository<BookedItem, Long> {
    @Query(value = "SELECT * FROM booked_items WHERE user_id = :userId ORDER BY booked_date DESC", nativeQuery = true)
    List<BookedItem> getBookedItemByuser(long userId);
}
