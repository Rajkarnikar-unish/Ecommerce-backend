package com.rajkarnikarunish.ecommercebackend.service;

import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EncryptionServiceTest {

    @Autowired
    private EncryptionService encryptionService;

    @Test
    @Transactional
    public void testPasswordEncryption() {
        String password = ""
    }
}
