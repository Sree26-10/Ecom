package com.Inventory_Service.service;


import com.Inventory_Service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {
    @Autowired
    private  InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public boolean isInstock(String skuCode){
      return   inventoryRepository.findByskuCode(skuCode).isPresent();
    }
}
