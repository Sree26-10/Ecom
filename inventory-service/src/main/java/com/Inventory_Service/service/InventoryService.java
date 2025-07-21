package com.Inventory_Service.service;


import com.Inventory_Service.dto.InventoryResponse;
import com.Inventory_Service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {
    @Autowired
    private  InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInstock(List<String> skuCode){
      return   inventoryRepository.findByskuCodeIn(skuCode).stream()
              .map(inventory ->
                  InventoryResponse.builder().skuCode(inventory.getSkuCode())
                          .isInstock(inventory.getQuantity()>0)
                          .build()).collect(Collectors.toList());
    }
    }

