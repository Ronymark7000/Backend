package com.project.JewelHub.bookeditem;

import com.project.JewelHub.user.User;
import com.project.JewelHub.util.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/booked-items")
public class BookedItemController {

    private final BookedItemService bookedItemService;

    @GetMapping("")
    public ResponseWrapper getAllBookedItemByUserId(HttpServletRequest request){
        User user = (User) request.getAttribute("user");
        int userId = user.getUserId();
        List<BookedItem> bookedItem = bookedItemService.getAllBookedItemByUser(userId);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(200);
        response.setMessage("Booked Order retrieved successfully");
        response.setSuccess(true);
        response.setResponse(bookedItem);
        return response;
    }

    @PostMapping("")
    public ResponseWrapper placeOrder(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        int userId = user.getUserId();

        try {
            return bookedItemService.placeAddBooking(userId);
        } catch (Exception exception) {
            return new ResponseWrapper(false, 500, "Error placing orders ", null);
        }
    }

    //    @GetMapping()
    //    public ResponseWrapper getOrdersByUser(HttpServletRequest request) {
    //        int userId = (int) request.getAttribute("userId");
    //        List<Order> orderList = orderService.getOrdersByUser(userId);
    //        return new ResponseWrapper(true, 200, "Orders fetched",orderList );
    //    }
}

