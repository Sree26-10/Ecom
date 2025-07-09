package com.Ecom.order_service.controller;

import com.Ecom.order_service.dto.OrderRequest;
import com.Ecom.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.junit.internal.requests.OrderingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
     private  OrderService  orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "Order Placed Successfully";
    }
}
