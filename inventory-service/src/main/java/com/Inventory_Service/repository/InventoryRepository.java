package com.Inventory_Service.repository;

import com.Inventory_Service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {



    List<Inventory> findByskuCodeIn(List<String> skuCode);
}
