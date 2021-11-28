package com.prabina.promo.promotionengine.repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.prabina.promo.promotionengine.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    ProductRepository productRepository;

    @Test
    public void shouldSaveAProduct() {
        productRepository.save(new Product());
    }

    @Test
    public void shouldFindProductBySku() {
        Product product = new Product();
        product.setSku("SKU123");
        product.setName("testProduct");
        product.setPrice(new BigDecimal(2));

        Product savedProduct = entityManager.persistAndFlush(product);

        assertEquals(savedProduct, productRepository.findBySku("SKU123").get());
    }
}
