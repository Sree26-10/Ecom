package com.Ecom.order_service.service;

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

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItemsDto> dtoList = Optional.ofNullable(orderRequest.getOrderLineItemsDtoList())
                .orElse(List.of());

        List<OrderLineItems> orderLineItemsList = dtoList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        order.setOrderLineItemsList(orderLineItemsList);
        orderRepository.save(order);
        //continue @1.06 mins
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }

}


