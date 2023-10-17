package com.rajkarnikarunish.ecommercebackend.service;

import com.rajkarnikarunish.ecommercebackend.models.LocalUser;
import com.rajkarnikarunish.ecommercebackend.models.ProductOrder;
import com.rajkarnikarunish.ecommercebackend.models.dao.ProductOrderDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private ProductOrderDao productOrderDao;

    public OrderService(ProductOrderDao productOrderDao) {
        this.productOrderDao = productOrderDao;
    }

    public List<ProductOrder> getOrders(LocalUser user) {
        return productOrderDao.findByUser(user);
    }
}
