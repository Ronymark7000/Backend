package com.project.JewelHub.bookeditem;

import com.project.JewelHub.booklist.Booklist;
import com.project.JewelHub.booklist.BooklistRepo;
import com.project.JewelHub.util.CustomException;
import com.project.JewelHub.util.ResponseWrapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookedItemService {

    private final BookedItemRepo bookedItemRepo;
    private final BooklistRepo booklistRepo;

    public List<BookedItem> getAllBookedItemByUser(Integer userId){
        return bookedItemRepo.getBookedItemByuser(userId);
    }

    public List<BookedItem> getOrdersByUser(long userId) {
        return bookedItemRepo.getBookedItemByuser(userId);
    }

    public List<BookedItem> getAllOrders(String sortBy, int orderBy) {
        try {
            if (orderBy == 1) {
                return bookedItemRepo.findAll(Sort.by(sortBy).ascending());
            } else {
                return bookedItemRepo.findAll(Sort.by(sortBy).descending());
            }
        } catch (Exception exception) {
            throw new CustomException("Error fetching order", 400);
        }
    }

    @Transactional
    public ResponseWrapper placeAddBooking(long userId) {
        List<Booklist> booklist = booklistRepo.findAllItemsByUser(userId);

        if (booklist == null) {
            return new ResponseWrapper(false, 400, "No item to place order", null);
        } else {
            List<BookedItem> bookeditem = new ArrayList<>();
            for (Booklist booklists : booklist) {
                BookedItem bookeditems = new BookedItem(booklists);
                bookeditem.add(bookeditems);
            }
            try {
                bookedItemRepo.saveAll(bookeditem);
                booklistRepo.deleteAll(booklist);
                return new ResponseWrapper(true, 200, "Booking Order Placed successfully", null);
            } catch (DataAccessException exception) {
                return new ResponseWrapper(false, 500, "Error placing booking order", null);
            }
        }
    }

    public ResponseWrapper updateBookedStatus(long bookedItemId, String status) {
        BookedItem bookedItem = bookedItemRepo.findById(bookedItemId).orElse(null);

        if (bookedItem == null) {
            return new ResponseWrapper(false, 400, "Can not find booking order at the moment", null);
        } else {
            bookedItem.setStatus(status);
            bookedItemRepo.save(bookedItem);
            return new ResponseWrapper(true, 200, "Booking Order updated successfully", null);
        }
    }
}
