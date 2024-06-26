package com.project.JewelHub.items;

import com.project.JewelHub.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class OpenItemController {

    private final ItemService itemService;

    /* ----------------------------------Note for Autowired services-------------------------------------*/
    //Auto Wired Constructor- (Not required to use @Autowired since we are using @RequiredArgsConstructor)
    /*
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }
    */

    /* ---------------------Handles request to retrieve all jewelry items from the inventory--------------------*/
    @GetMapping("/items")
    private ResponseEntity<ResponseWrapper> getAllItems() {
        List<ItemDto> item = itemService.getAllItems();
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setSuccess(true);
        response.setMessage("Item Collected successfully");
        response.setResponse(item);
        return ResponseEntity.ok(response);
    }

    /* ---------------------Handles request to retrieve particular jewelry items by its item ID--------------------*/
    @GetMapping("/item/{itemCode}")
    private ResponseEntity<ResponseWrapper> getItemByCode(@PathVariable("itemCode") int itemCode) {

        Item item = itemService.getItemByCode(itemCode);
        if (item != null){
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Item Collected successfully by ID");
            response.setResponse(itemService.getItemByCode(itemCode));
            return ResponseEntity.ok(response);
        }else {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Item not found...Please Check Again");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}