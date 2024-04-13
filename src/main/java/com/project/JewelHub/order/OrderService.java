package com.project.JewelHub.order;

import com.project.JewelHub.util.CustomMapper;
import com.project.JewelHub.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;
    List <Order> order = new ArrayList<>();

    public List<Order> getAllOrders() {
        return orderRepo.findAll(Sort.by("orderId"));
    }

    public Order getOrdersById (int orderId){
        Optional<Order> optionalOrder = orderRepo.findById(orderId);
        return optionalOrder.orElse(null);
    }

    public Order saveOrder(Order order){
        return orderRepo.save(order);
    }

    public Order updateOrder(int orderId, Order updatedOrder){
        Optional<Order> optionalOrder = orderRepo.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order existingOrder = optionalOrder.get();

            // Update properties of the existing user with values from the updated user
            existingOrder.setOrderId(orderId);
            existingOrder.setOrderDate(updatedOrder.getOrderDate());
            existingOrder.setCustomerName(updatedOrder.getCustomerName());
            existingOrder.setContact(updatedOrder.getContact());
            existingOrder.setOrderName(updatedOrder.getOrderName());
            existingOrder.setDescription(updatedOrder.getDescription());
            existingOrder.setEstimatedWeight(updatedOrder.getEstimatedWeight());
            existingOrder.setAdvancePayment(updatedOrder.getAdvancePayment());
            existingOrder.setDeliveryDate(updatedOrder.getDeliveryDate());
            existingOrder.setOrderStatus(updatedOrder.isOrderStatus());
            return orderRepo.save(existingOrder);

        }
        return null;
    }

    public ResponseWrapper deleteOrder(int orderId){
        Optional<Order> optionalOrder = orderRepo.findById(orderId);
        if (optionalOrder.isPresent()){
            orderRepo.deleteById(orderId);
        }
        return null;
    }
}
