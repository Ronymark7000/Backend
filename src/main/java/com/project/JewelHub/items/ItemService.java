package com.project.JewelHub.items;

import com.project.JewelHub.util.CustomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
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
        List<Item> item = new ArrayList<>(itemRepo.findAll());

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
                item.getKarat(),
                item.getGrossWeight(),
                item.getWastage(),
                item.getNetWeight(),
                item.getGoldPrice(),
                item.getCostOfStone(),
                item.getManufactureCost(),
                item.getDescription(),
                item.getTotalCost(),
                item.getItemImageUrl()
        );
    }



    /*------------------------------Method to Add Jewelry Items to the Inventory---------------------*/
    public ItemDto addItem(ItemDto itemDto, MultipartFile imageFile) throws IOException{
        String itemImageUrl = saveImageLocally(imageFile);
        System.out.println("ImageUrl---->"+ itemImageUrl);

        Item newItem = new Item();
        newItem.setItemName(itemDto.getItemName());
        newItem.setMaterial(itemDto.getMaterial());
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

        Item savedItem =  itemRepo.save(newItem);

        System.out.println("Item Name"+savedItem.getItemName());

        return new ItemDto(savedItem.getItemCode(), savedItem.getItemName(), savedItem.getMaterial(), savedItem.getKarat(), savedItem.getGrossWeight(), savedItem.getWastage(), savedItem.getNetWeight(), savedItem.getGoldPrice(), savedItem.getCostOfStone(), savedItem.getManufactureCost(), savedItem.getDescription(), savedItem.getTotalCost());
    }


    /*------------------------------Method to update Jewelry Items to the Inventory---------------------*/
    public Item updateItem(int itemCode, Item updatedItem) {
        Optional<Item> optionalItem = itemRepo.findById(itemCode);
        if (optionalItem.isPresent()) {
            Item existingItem = optionalItem.get();

            // Update properties of the existing items with values from the updated item data

            existingItem.setItemName(updatedItem.getItemName());
            existingItem.setMaterial(updatedItem.getMaterial());
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
//        final String UPLOAD_DIRS = new ClassPathResource("static/itemImage").getFile().getAbsolutePath();
        final String UPLOAD_DIRS = "/Users/roheetshakya/Documents/Development/JewelHub/src/main/resources/static/itemImage";
        System.out.println(UPLOAD_DIRS );
        try {
            Files.copy(multipartFile.getInputStream(), Paths.get(UPLOAD_DIRS + File.separator + multipartFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw e; // Rethrow the exception for handling at a higher level
        }
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/itemImage/").path(Objects.requireNonNull(multipartFile.getOriginalFilename())).toUriString();
    }

}












