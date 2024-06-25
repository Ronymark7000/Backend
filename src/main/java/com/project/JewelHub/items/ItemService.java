package com.project.JewelHub.items;

import com.project.JewelHub.util.CustomMapper;
import lombok.RequiredArgsConstructor;
//import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService{

    private final ItemRepo itemRepo;

    /*------------------------------Method to Display All Jewelry Items---------------------*/
    public List<ItemDto> getAllItems() {
        List<Item> item = new ArrayList<>(itemRepo.findAll(Sort.by("itemCode")));

        List<ItemDto> itemDtos= CustomMapper.mapItemDtos(item);
        return itemDtos;
    }

    /*------------------------------Method to Display Jewelry Items By Code---------------------*/
    public Item getItemByCode(Integer itemCode){
        Optional<Item> optionalItem = itemRepo.findById(itemCode);
        Item item = optionalItem.get();
        return new Item(
                item.getItemCode(),
                item.getItemName(),
                item.getMaterial(),
                item.getCategory(),
                item.getKarat(),
                item.getGrossWeight(),
                item.getWastage(),
                item.getNetWeight(),
                item.getGoldPrice(),
                item.getCostOfStone(),
                item.getManufactureCost(),
                item.getDescription(),
                item.getTotalCost(),
                item.getItemImageUrl(),
                item.isAvailable()
//                ,item.getItemVideoUrl()
        );
    }



    /*------------------------------Method to Add Jewelry Items to the Inventory---------------------*/
    public ItemDto addItem(ItemDto itemDto, MultipartFile imageFile) throws IOException{
//        , MultipartFile videoFile
        String itemImageUrl = saveImageLocally(imageFile);
        System.out.println("ImageUrl---->"+ itemImageUrl);

//        String itemVideoUrl = saveVideoLocally(videoFile);
//        System.out.println("VideoUrl---->"+ itemVideoUrl);

        Item newItem = new Item();
        newItem.setItemName(itemDto.getItemName());
        newItem.setMaterial(itemDto.getMaterial());
        newItem.setCategory(itemDto.getCategory());
        newItem.setKarat(itemDto.getKarat());
        newItem.setGrossWeight(itemDto.getGrossWeight());
        newItem.setWastage(itemDto.getWastage());
        newItem.setNetWeight(itemDto.getNetWeight());
        newItem.setGoldPrice(itemDto.getGoldPrice());
        newItem.setCostOfStone(itemDto.getCostOfStone());
        newItem.setManufactureCost(itemDto.getManufactureCost());
        newItem.setDescription(itemDto.getDescription());
        newItem.setTotalCost(itemDto.getTotalCost());
        newItem.setItemImageUrl(itemImageUrl);
//        newItem.setItemVideoUrl(itemVideoUrl);

        Item savedItem =  itemRepo.save(newItem);

        System.out.println("Item Name"+savedItem.getItemName());

        return new ItemDto(savedItem.getItemCode(), savedItem.getItemName(), savedItem.getMaterial(), savedItem.getCategory(), savedItem.getKarat(), savedItem.getGrossWeight(), savedItem.getWastage(), savedItem.getNetWeight(), savedItem.getGoldPrice(), savedItem.getCostOfStone(), savedItem.getManufactureCost(), savedItem.getDescription(), savedItem.getTotalCost(), savedItem.getItemImageUrl(), savedItem.isAvailable());
//        , savedItem.getItemVideoUrl()
    }


    /*------------------------------Method to update Jewelry Items to the Inventory---------------------*/
    public Item updateItem(int itemCode, Item updatedItem) {
        Optional<Item> optionalItem = itemRepo.findById(itemCode);
        if (optionalItem.isPresent()) {
            Item existingItem = optionalItem.get();

            // Update properties of the existing items with values from the updated item data

            existingItem.setItemName(updatedItem.getItemName());
            existingItem.setMaterial(updatedItem.getMaterial());
            existingItem.setCategory(updatedItem.getCategory());
            existingItem.setKarat(updatedItem.getKarat());
            existingItem.setGrossWeight(updatedItem.getGrossWeight());
            existingItem.setWastage(updatedItem.getWastage());

            existingItem.setNetWeight(updatedItem.getNetWeight());
            existingItem.setGoldPrice(updatedItem.getGoldPrice());
            existingItem.setCostOfStone(updatedItem.getCostOfStone());
            existingItem.setManufactureCost(updatedItem.getManufactureCost());
            existingItem.setDescription(updatedItem.getDescription());
            existingItem.setTotalCost(updatedItem.getTotalCost());
            existingItem.setItemImageUrl(updatedItem.getItemImageUrl());

            // Save the updated item back to the database
            return itemRepo.save(existingItem);
        } else {
            // Handle the scenario when the item with the given itemCode is not found
            return  null;
        }
    }

    public Item updateItemAvailability(int itemCode, boolean available) {
        Optional<Item> optionalItem = itemRepo.findById(itemCode);
        if (optionalItem.isPresent()) {
            Item existingItem = optionalItem.get();

            // Update the availability status
            existingItem.setAvailable(available);

            // Save the updated item back to the database
            return itemRepo.save(existingItem);
        } else {
            // Handle the scenario when the item with the given itemCode is not found
            return null;
        }
    }

    /*------------------------------Method to delete Jewelry Items to the Inventory---------------------*/

    /* ############---NEEDS REFACTORING OF THE CODE------##########*/
    public Item deleteItem(int itemCode) {
        Optional<Item> optionalItem = itemRepo.findById(itemCode);
        if (optionalItem.isPresent()) {
            itemRepo.deleteById(itemCode);
        }
        return  null;
    }

    /*------------------------------Method to Save Image Locally---------------------*/
    public String saveImageLocally(MultipartFile multipartFile) throws IOException {
        System.out.println("Is image working");
        final String UPLOAD_DIRS = "D:\\Development\\JewelHub\\src\\main\\resources\\static\\itemImage";
//        final String UPLOAD_DIRS = new ClassResourcePath("/static/itemImage").getFile().getAbsolutePath();

        System.out.println(UPLOAD_DIRS );
        try {
            Files.copy(multipartFile.getInputStream(), Paths.get(UPLOAD_DIRS + File.separator + multipartFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw e; // Rethrow the exception for handling at a higher level
        }
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/itemImage/").path(Objects.requireNonNull(multipartFile.getOriginalFilename())).toUriString();
    }


    public String saveVideoLocally(MultipartFile multipartFile) throws IOException {
        // Define the directory where you want to save the videos
        final String UPLOAD_VID_DIR = "D:\\Development\\JewelHub\\src\\main\\resources\\static\\itemVideo";
        System.out.println(UPLOAD_VID_DIR );
        try {
            // Copy the video file to the specified directory
            Files.copy(multipartFile.getInputStream(),
                    Paths.get(UPLOAD_VID_DIR + File.separator + multipartFile.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw e; // Rethrow the exception for handling at a higher level
        }

        // Return the URL of the saved video
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/video/")
                .path(Objects.requireNonNull(multipartFile.getOriginalFilename()))
                .toUriString();
    }

}












