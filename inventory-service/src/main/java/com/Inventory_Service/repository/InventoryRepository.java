package com.Inventory_Service.repository;

import com.Inventory_Service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    Optional<Inventory> findByskuCode(String skuCode);
}
