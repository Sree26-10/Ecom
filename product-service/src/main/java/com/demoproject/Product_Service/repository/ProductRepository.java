package com.demoproject.Product_Service.repository;

import com.demoproject.Product_Service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,String> {
}
