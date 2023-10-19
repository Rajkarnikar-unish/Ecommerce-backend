package com.rajkarnikarunish.ecommercebackend.models.dao;

import com.rajkarnikarunish.ecommercebackend.models.VerificationToken;
import org.springframework.data.repository.ListCrudRepository;

public interface VerificationTokenDao extends ListCrudRepository<VerificationToken, Long> {
}
