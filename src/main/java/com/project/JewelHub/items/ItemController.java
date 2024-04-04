package com.project.JewelHub.items;

import com.project.JewelHub.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

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
        response.setSuccess(true);
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Item Collected successfully");
        response.setResponse(item);
        return ResponseEntity.ok(response);
    }

    /* ---------------------Handles request to retrieve particular jewelry items by its item ID--------------------*/
    @GetMapping("/item/{itemCode}")
    private ResponseEntity<ResponseWrapper> getItemByCode(@PathVariable("itemCode") int itemCode) {

        Item item = itemService.getItemByCode(itemCode);
        if (item != null){
            // Fetch the image URL associated with the item
            String imageUrl = item.getItemImageUrl();
            String videoUrl = item.getItemVideoUrl();

            // Create a new ItemDto with the image URL included
            Item itemWithImage = new Item(
                    item.getItemCode(),
                    item.getItemName(),
                    item.getMaterial(),
                    item.getKarat(),
                    item.getGrossWeight(),
                    item.getWastage(),
                    item.getNetWeight(),
                    item.getGoldPrice(),
                    item.getCostOfStone(),
                    item.getManufactureCost(),
                    item.getDescription(),
                    item.getTotalCost(),
                    imageUrl, // Include the image URL here
                    videoUrl
            );

            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.OK.value());
            response.setMessage("Item Collected successfully by ID");
//            response.setResponse(itemService.getItemByCode(itemCode));
            response.setResponse(itemWithImage);
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
    private ResponseEntity<ResponseWrapper<ItemDto>> additem(@Valid @ModelAttribute ItemDto itemDto, @RequestParam("itemImage") MultipartFile itemImage, @RequestParam("itemVideo") MultipartFile itemVideo) {
        ResponseWrapper<ItemDto> response = new ResponseWrapper<>();
        try {
            if (itemImage.isEmpty()) {
                response.setSuccess(false);
                response.setStatusCode(HttpStatus.NO_CONTENT.value());
                response.setMessage("Image Not Found, Upload Image");
                response.setResponse(null);
                return ResponseEntity.ok(response);
            }
                response.setSuccess(true);
                response.setStatusCode(HttpStatus.CREATED.value());
//                response.setMessage("Successfully Added where Item Code: " + item.getItemCode());
                response.setResponse(itemService.addItem(itemDto, itemImage, itemVideo));
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            // Handle other exceptions as needed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
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