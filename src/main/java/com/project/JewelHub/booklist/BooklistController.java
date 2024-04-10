package com.project.JewelHub.booklist;

import com.project.JewelHub.items.Item;
import com.project.JewelHub.user.User;
import com.project.JewelHub.util.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booklist")
@RequiredArgsConstructor
public class BooklistController {

    private final BooklistService booklistService;

    @GetMapping("")
    public ResponseWrapper getAllFromBooklist(HttpServletRequest request) {
        // Retrieve the user object from the request
        User user = (User) request.getAttribute("user");

        // Check if the user object is not null
        if (user != null) {
            // Extract the user ID from the user object
            int userId = user.getUserId();

            // Fetch items from the user's booklist using the user ID
            List<BooklistDto> booklistItems = booklistService.getItemsFromList(userId);

            // Return a response with the fetched items
            return new ResponseWrapper(true, 200, "Cart fetched successfully", booklistItems);
        } else {
            // User object not found in request, return error response
            return new ResponseWrapper(false, 400, "User attribute not found in request", null);
        }
    }

    @PostMapping("")
    public ResponseWrapper addToCart(@RequestBody int itemCode, HttpServletRequest request) {
        // Retrieve the user object from the request
        User user = (User) request.getAttribute("user");

        // Check if the user object is not null
        if (user != null) {
            try {
                // Extract the user ID from the user object
                int userId = user.getUserId();

                // Check if the item is already in the user's cart
                if (booklistService.findSingleBooklistByUser(userId, itemCode) == null) {
                    // Create a new cart item
                    Booklist newCart = new Booklist();
                    Item item = new Item();

                    // Set the item code and user ID
                    item.setItemCode(itemCode);
                    newCart.setItem(item);
                    newCart.setUser(user);

                    // Add the item to the cart and return success response
                    return new ResponseWrapper(true, 200, "Item added to BookList", booklistService.addItemToBooklist(newCart));
                } else {
                    // Item is already in the cart, return error response
                    return new ResponseWrapper(false, 400, "Item already in BookList", null);
                }
            } catch (NumberFormatException e) {
                // Handle the case where userIdString cannot be parsed to an integer
                return new ResponseWrapper(false, 400, "Invalid userId format", null);
            }
        } else {
            // User object not found in request, return error response
            return new ResponseWrapper(false, 400, "User attribute not found in request", null);
        }
    }


    @DeleteMapping("/{bookListId}")
    public ResponseWrapper deleteBookFromCart(@PathVariable long bookListId, HttpServletRequest request)  {
        Integer userId = (Integer) request.getAttribute("userId");
        booklistService.removeItemFromBooklist(bookListId, userId);
        //        response.setStatusCode(HttpStatus.OK.value());
//        response.setMessage("User deleted successfully");
//        response.setResponse(cartId);
        return new ResponseWrapper(true, 200, ("Cart Deleted Successfully"), bookListId);

    }

}
