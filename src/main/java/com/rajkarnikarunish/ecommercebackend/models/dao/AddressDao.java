package com.rajkarnikarunish.ecommercebackend.models.dao;

import com.rajkarnikarunish.ecommercebackend.models.Address;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface AddressDao extends ListCrudRepository<Address, Long> {

    List<Address> findByLocalUser_Id(Long id);

}
