package com.prabina.promo.promotionengine.services;

import static org.junit.jupiter.api.Assertions.*;

import com.prabina.promo.promotionengine.dtos.GetProduct;
import com.prabina.promo.promotionengine.entities.Product;
import com.prabina.promo.promotionengine.entities.Promotion;
import com.prabina.promo.promotionengine.promoEngine.PromotionType;
import com.prabina.promo.promotionengine.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;


    private static Promotion promotion1;
    private static Promotion promotion2;
    private static Product product1;
    private static Product product2;
    private static Product product3;
    private static Set<Product> productSet1;
    private static Set<Product> productSet2;
    private static List<Product> allProducts = new ArrayList<>();

    @BeforeAll
    static void setup() {
        promotion1 = new Promotion();
        promotion1.setPromoType(PromotionType.DIFFERENT_ITEMS_FIXED_PRICE);
        promotion1.setDiscount(BigDecimal.valueOf(5));
        promotion1.setPromoCode("PROMO1");

        promotion2 = new Promotion();
        promotion2.setPromoType(PromotionType.SAME_ITEM_FIXED_PRICE);
        promotion2.setDiscount(BigDecimal.valueOf(5));
        promotion2.setPromoCode("PROMO2");

        product1 = new Product();
        product1.setName("product1");
        product1.setPrice(new BigDecimal(10));
        product1.setSku("SKU1");
        product1.setPromotion(promotion1);

        product2 = new Product();
        product2.setName("product2");
        product2.setPrice(new BigDecimal(10));
        product2.setSku("SKU2");
        product2.setPromotion(promotion1);

        product3 = new Product();
        product3.setName("product3");
        product3.setPrice(new BigDecimal(5));
        product3.setSku("SKU3");
        product3.setPromotion(promotion2);

        allProducts.add(product1);
        allProducts.add(product2);
        allProducts.add(product3);

        productSet1 = new HashSet<>();
        productSet1.add(product1);
        productSet1.add(product2);

        productSet2 = new HashSet<>();
        productSet2.add(product3);

        promotion1.setProducts(productSet1);
        promotion2.setProducts(productSet2);
    }

    @Test
    public void shouldReturnAllProducts() {
        doReturn(allProducts).when(productRepository).findAll();

        List<GetProduct> response = productService.getAllProducts();

        assertEquals(3, response.size());
    }

}
