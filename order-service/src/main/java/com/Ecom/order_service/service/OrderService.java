package com.Ecom.order_service.service;

import com.Ecom.order_service.dto.InventoryResponse;
import com.Ecom.order_service.dto.OrderLineItemsDto;
import com.Ecom.order_service.dto.OrderRequest;
import com.Ecom.order_service.model.Order;
import com.Ecom.order_service.model.OrderLineItems;
import com.Ecom.order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItemsDto> dtoList = Optional.ofNullable(orderRequest.getOrderLineItemsDtoList())
                .orElse(List.of());

        List<OrderLineItems> orderLineItemsList = dtoList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        order.setOrderLineItemsList(orderLineItemsList);
        List<String> skuCodes= order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();
       InventoryResponse[] inventoryResponsesArray= webClient.get()
                .uri("http://localhost:8083/api/inventory",uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build() )
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        assert inventoryResponsesArray != null;
        boolean allProductsInStock=  Arrays.stream(inventoryResponsesArray).allMatch(InventoryResponse::isInstock);

       if(Boolean.TRUE.equals(allProductsInStock)){
        orderRepository.save(order);}
       else{
           throw  new IllegalArgumentException("Product is not in stock,Please try again later");
       }

        // Should call the Inventory Service, and place the order if it is in stock

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }

}


