package com.prabina.promo.promotionengine.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.prabina.promo.promotionengine.dtos.AddToCartRequest;
import com.prabina.promo.promotionengine.dtos.AddToCartResponse;
import com.prabina.promo.promotionengine.entities.Cart;
import com.prabina.promo.promotionengine.entities.CartItem;
import com.prabina.promo.promotionengine.entities.Product;
import com.prabina.promo.promotionengine.exceptions.CartNotFoundException;
import com.prabina.promo.promotionengine.exceptions.ExceptionMessages;
import com.prabina.promo.promotionengine.exceptions.ProductNotFoundException;
import com.prabina.promo.promotionengine.repositories.CartItemRepository;
import com.prabina.promo.promotionengine.repositories.CartRepository;
import com.prabina.promo.promotionengine.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.*;

@SpringBootTest
public class CartItemServiceTest {

    @InjectMocks
    CartItemService cartItemService;

    @Mock
    CartRepository cartRepository;

    @Mock
    ProductRepository productRepository;

    private static Product product1;
    private static Product product2;
    private static Product product3;
    private static List<Product> allProducts = new ArrayList<>();
    private static Cart cart = new Cart();
    private static CartItem item1 = new CartItem();
    private static CartItem item2 = new CartItem();
    private static CartItem item3 = new CartItem();

    @BeforeAll
    static void setUp(){
        product1 = new Product();
        product1.setId(Long.valueOf(1));
        product1.setName("product1");
        product1.setPrice(new BigDecimal(10));
        product1.setSku("SKU1");

        product2 = new Product();
        product2.setId(Long.valueOf(2));
        product2.setName("product2");
        product2.setPrice(new BigDecimal(10));
        product2.setSku("SKU2");

        product3 = new Product();
        product3.setId(Long.valueOf(3));
        product3.setName("product3");
        product3.setPrice(new BigDecimal(5));
        product3.setSku("SKU3");

        allProducts.add(product1);
        allProducts.add(product2);
        allProducts.add(product3);

        cart.setReference("testRef");
        cart.setUsername("test user");
        cart.setCartItems(new HashSet<>());

        item1.setCart(cart);
        item1.setQuantity(1);
        item1.setProduct(product1);

        item2.setCart(cart);
        item2.setQuantity(2);
        item2.setProduct(product2);

        item3.setCart(cart);
        item3.setQuantity(2);
        item3.setProduct(product3);

        Set<CartItem> itemSet = new HashSet<>();
        itemSet.add(item1);
        itemSet.add(item2);
        cart.setCartItems(itemSet);
    }

    @Test
    public void shouldAddItemToCart() throws Exception, CartNotFoundException {
        AddToCartRequest request = new AddToCartRequest();
        request.setSku("SKU1");
        request.setQty(3);
        when(cartRepository.findByReference("testRef")).thenReturn(Optional.of(cart));
        when(productRepository.findBySku("SKU1")).thenReturn(Optional.of(product1));

        AddToCartResponse response = cartItemService.addToCart("testRef", request);

        assertNotNull(response.getName());
        assertNotNull(response.getPrice());
    }

    @Test
    public void shouldFailWithExceptionForInvalidCart() {
        AddToCartRequest request = new AddToCartRequest();
        request.setSku("SKU1");
        request.setQty(3);
        when(productRepository.findBySku("SKU1")).thenReturn(Optional.of(product1));

        CartNotFoundException exception = assertThrows(CartNotFoundException.class, () -> {
            cartItemService.addToCart("invalid-ref", request);
        });

        String expectedMessage = ExceptionMessages.CART_NOT_FOUND;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void shouldFailWithExceptionForInvalidProduct() {
        AddToCartRequest request = new AddToCartRequest();
        request.setSku("invalidSku");
        request.setQty(3);
        when(cartRepository.findByReference("testRef")).thenReturn(Optional.of(cart));

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            cartItemService.addToCart("testRef", request);
        });

        String expectedMessage = ExceptionMessages.PRODUCT_NOT_FOUND;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


}
