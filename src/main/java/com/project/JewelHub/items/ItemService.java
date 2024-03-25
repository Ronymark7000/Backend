package com.project.JewelHub.items;

import com.project.JewelHub.util.CustomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService{

    private final ItemRepo itemRepo;

    /*------------------------------Method to Display All Jewelry Items---------------------*/
    public List<ItemDto> getAllItems() {
        List<Item> item = new ArrayList<>(itemRepo.findAll());

        List<ItemDto> itemDtos= CustomMapper.mapItemDto(item);
        return itemDtos;
    }

    /*------------------------------Method to Display Jewelry Items By Code---------------------*/
    public ItemDto getItemByCode(Integer itemCode){
        Optional<Item> optionalItem = itemRepo.findById(itemCode);
        Item item = optionalItem.get();
        return new ItemDto(
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
                item.getTotalCost()
        );
    }

    /*------------------------------Method to Add Jewelry Items to the Inventory---------------------*/
    public Item addItem(Item item){
        return itemRepo.save(item);
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

            // Save the updated item back to the database
            Item savedItem = itemRepo.save(existingItem);
            return savedItem;
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

}












