package com.project.JewelHub.items;

import com.project.JewelHub.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ItemController {

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
        response.setMessage("Item Collected successfully");
        response.setResponse(item);
        return ResponseEntity.ok(response);
    }

    /* ---------------------Handles request to retrieve particular jewelry items by its item ID--------------------*/
    @GetMapping("/item/{itemCode}")
    private ResponseEntity<ResponseWrapper> getItemByCode(@PathVariable("itemCode") int itemCode) {

        ItemDto item = itemService.getItemByCode(itemCode);
        if (item != null){
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.OK.value());
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

    /* ---------------------Handles request to add particular jewelry item to the inventory-------------------*/
    @PostMapping("/item")
    private ResponseEntity<ResponseWrapper> addBook(@RequestBody Item item){
        //bookService.addBook(book);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Successfully Added where Book Id: " + item.getItemCode() );
        response.setResponse(itemService.addItem(item));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /* ---------------------Handles request to update existing jewelry item in the inventory-------------------*/
    @PutMapping("/item/{itemCode}")
    private ResponseEntity<ResponseWrapper> updateItem(@PathVariable ("itemCode") int itemCode, @RequestBody Item item){
        Item updatedItem = itemService.updateItem(itemCode,item);
        if (updatedItem != null){
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Item updated successfully");
            response.setResponse(item);
            return ResponseEntity.ok(response);
        } else {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Item not found...Please Check Again");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /* ---------------------Handles request to delete a particular jewelry item in the inventory-------------------*/

    /* ############---NEEDS REFACTORING OF THE CODE------##########*/
    @DeleteMapping("/item/{itemCode}")
    private ResponseEntity<ResponseWrapper> deleteItem(@PathVariable("itemCode")int itemCode){
        itemService.deleteItem(itemCode);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Item deleted successfully");
        return ResponseEntity.ok(response);
    }
}