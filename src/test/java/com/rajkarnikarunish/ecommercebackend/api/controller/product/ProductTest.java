package com.rajkarnikarunish.ecommercebackend.api.controller.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@AutoConfigureMockMvc //Tells spring I want use mvc framework for unit testing, we need to autowire mock mvc object
public class ProductTest {

//    @Autowired
//    private MockMvc mvc;
//
//    @Test
//    public void testProductList() throws Exception {
//        mvc.perform(get("/product")).andExpect(status().is(HttpStatus.OK.value()));
//    }

}
