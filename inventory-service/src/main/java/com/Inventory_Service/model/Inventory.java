package com.Inventory_Service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="t_inventory")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private  String skuCode;
    private Integer quantity;
}
