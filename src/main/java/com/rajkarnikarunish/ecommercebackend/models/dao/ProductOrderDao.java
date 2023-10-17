package com.rajkarnikarunish.ecommercebackend.models.dao;

import com.rajkarnikarunish.ecommercebackend.models.LocalUser;
import com.rajkarnikarunish.ecommercebackend.models.ProductOrder;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface ProductOrderDao extends ListCrudRepository<ProductOrder, Long> {
    List<ProductOrder> findByUser(LocalUser user);
}
