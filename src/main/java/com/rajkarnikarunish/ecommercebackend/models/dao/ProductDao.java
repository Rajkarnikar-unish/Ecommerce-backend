package com.rajkarnikarunish.ecommercebackend.models.dao;

import com.rajkarnikarunish.ecommercebackend.models.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductDao extends ListCrudRepository<Product, Long> {
}
