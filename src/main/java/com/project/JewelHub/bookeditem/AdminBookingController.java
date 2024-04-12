package com.project.JewelHub.bookeditem;

import com.project.JewelHub.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin/booked-items")
public class AdminBookingController {

    private final BookedItemService bookedItemService;

    @GetMapping("")
    public ResponseWrapper getAllOrders(@RequestParam(name = "sort", defaultValue = "bookedDate") String sort,
                                        @RequestParam(name = "bookedItem", defaultValue = "1") int bookedItem) {
        return new ResponseWrapper(true,200,"All orders fetched", bookedItemService.getAllOrders(sort, bookedItem));
    }

    @PutMapping("/{bookedItemId}")
    public ResponseWrapper updateOrderStatus(@PathVariable long bookedItemId, @RequestBody String status) {
        String statusStr = status.replace("\"", "");
        return bookedItemService.updateBookedStatus(bookedItemId, statusStr);
    }
}


