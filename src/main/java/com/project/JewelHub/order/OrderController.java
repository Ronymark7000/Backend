package com.project.JewelHub.order;

import com.project.JewelHub.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/order")
    public ResponseWrapper getAllOrders() {
        orderService.getAllOrders();
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setSuccess(true);
        response.setMessage("All Orders retrieved successfully");
        response.setResponse(orderService.getAllOrders());
        return response;
    }

    @GetMapping("/order/{orderId}")
    public ResponseWrapper getOrderById(@PathVariable int orderId) {
        Order order = orderService.getOrdersById(orderId);
        if (order != null) {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Order retrieved successfully");
            response.setResponse(order);
            return response;
        } else {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Order not found");
            return response;
        }
    }

    @PostMapping("/order")
    public ResponseWrapper addOrders(@Valid @RequestBody Order order) {
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Successfully Added Order where Order Id:" + order.getOrderId());
        order.setOrderStatus(false);
        response.setResponse(orderService.saveOrder(order));
        response.setSuccess(true);
        return response;
    }

    @PutMapping("/order/{orderId}")
    public ResponseWrapper<Order> updatOrder(@PathVariable int orderId, @Valid @RequestBody Order updatedOrder) {
        Order optionalOrder = orderService.updateOrder(orderId, updatedOrder);
        if (optionalOrder != null) {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Order updated successfully");
            response.setResponse(optionalOrder);
            return response;
        } else {
            ResponseWrapper response = new ResponseWrapper();
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Order not found");
            response.setSuccess(false);
            return response;
        }
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseWrapper deleteUser(@PathVariable("orderId") int orderId) {
        orderService.deleteOrder(orderId);
        ResponseWrapper response = new ResponseWrapper();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Order deleted successfully");
        return response;
    }


}
