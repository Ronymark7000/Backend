package com.project.JewelHub.util;

import com.project.JewelHub.items.ItemDto;
import com.project.JewelHub.items.Item;
import com.project.JewelHub.order.Order;
import com.project.JewelHub.user.User;
import com.project.JewelHub.user.UserRepo;
import com.project.JewelHub.user.dtos.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CustomMapper {
    public static UserDto mapUserDto(User user) {

        return new UserDto(user.getUserId(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getRole(), user.getContact(), user.isEnabled());
    }

    public static List<UserDto> mapUserDtos(List<User> users) {

        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(CustomMapper.mapUserDto(user));
        }
        return userDtos;
    }

    public static ItemDto mapItemDto(Item item) {
        return new ItemDto(
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

    public static List<ItemDto> mapItemDtos(List<Item> items) {

        List<ItemDto> itemDtos = new ArrayList<>();
        for (Item item : items) {
            itemDtos.add(CustomMapper.mapItemDto(item));
        }
        return itemDtos;
    }

    public static Order mapOrders(Order order) {

        return new Order(order.getOrderId(), order.getOrderDate(), order.getCustomerName(), order.getContact(), order.getOrderName(), order.getDescription(), order.getEstimatedWeight(), order.getAdvancePayment(), order.getDeliveryDate(), order.isOrderStatus());
    }

        @Service
        @RequiredArgsConstructor
        public static class CustomUserDetailService implements UserDetailsService {

            private final UserRepo userRepo;

            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                Optional<User> userOptional = userRepo.findUserByEmail(email);
                User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
                return new CustomUserDetail(user);
            }

        }
    }





