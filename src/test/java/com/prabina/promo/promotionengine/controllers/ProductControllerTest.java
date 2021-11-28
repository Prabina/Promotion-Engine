package com.prabina.promo.promotionengine.controllers;

import static org.junit.jupiter.api.Assertions.*;

import com.prabina.promo.promotionengine.dtos.GetProduct;
import com.prabina.promo.promotionengine.services.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class ProductControllerTest {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;

    @Test
    public void shouldReturnAllProducts() {
        List<GetProduct> getProducts = new ArrayList<>();

        doReturn(getProducts).when(productService).getAllProducts();

        ResponseEntity<List<GetProduct>> responseEntity = productController.getAllProducts();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(getProducts, responseEntity.getBody());
    }
}
