package com.project.JewelHub.booklist;

import com.project.JewelHub.util.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BooklistService {

    private final BooklistRepo booklistRepo;

    public List<BooklistDto> getItemsFromList(int userId) {
        List<Booklist> allbookList = booklistRepo.findAllItemsByUser(userId);
        List<BooklistDto> bookResDtoList = new ArrayList<>();
        for (Booklist booklist : allbookList) {
            bookResDtoList.add(new BooklistDto(booklist.getBookListID(), booklist.getItem()));
        }
        return bookResDtoList;
    }

    public Booklist findSingleBooklistByUser(int userId, int itemCode){
        return  booklistRepo.findBooklistByUserIdAndItemCode(userId, itemCode);
    }

    public BooklistDto addItemToBooklist(Booklist booklist) {
        try {
            if (booklist != null) {
                Booklist newBooklist = booklistRepo.save(booklist);
                return new BooklistDto(newBooklist.getBookListID(), newBooklist.getItem());
            } else {
                throw new IllegalArgumentException("Invalid Booklist object provided");
            }
        } catch (Exception e) {
            throw new CustomException("Failed to add item to booklist", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }


    public boolean removeItemFromBooklist(long bookListId, int userId) {
        try {
            Booklist booklist = booklistRepo.findById((int) bookListId).orElse(null);
            if (booklist != null && booklist.getUser().getUserId() == userId) {
                booklistRepo.deleteById((int)bookListId);
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }

//    public void removeItemFromBooklist(Long bookListId, Integer userId) {
//        Optional<Booklist> optionalCart = booklistRepo.findById(bookListId);
//        if (optionalCart.isPresent()) {
//            Booklist existingCart = optionalCart.get();
//            if (existingCart.getUser().getUserId() == userId) {
//                booklistRepo.deleteById(bookListId);
//            } else {
//
//                throw new CustomException(" Booking ID not found of: " + bookListId);
//            }
//        }
//    }


}
