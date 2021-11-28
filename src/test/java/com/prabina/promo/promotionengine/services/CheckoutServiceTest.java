package com.prabina.promo.promotionengine.services;

import static org.junit.jupiter.api.Assertions.*;

import com.prabina.promo.promotionengine.dtos.CheckoutResponse;
import com.prabina.promo.promotionengine.entities.Cart;
import com.prabina.promo.promotionengine.entities.CartItem;
import com.prabina.promo.promotionengine.entities.Product;
import com.prabina.promo.promotionengine.entities.Promotion;
import com.prabina.promo.promotionengine.promoEngine.FixedPriceForDifferentItems;
import com.prabina.promo.promotionengine.promoEngine.PromoStrategy;
import com.prabina.promo.promotionengine.promoEngine.PromotionType;
import com.prabina.promo.promotionengine.repositories.CartRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CheckoutServiceTest {

    private static CheckoutService checkoutService;

    @Mock
    private static CartRepository cartRepository;

    private static Promotion promotion;
    private static Product product1;
    private static Product product2;
    private static Set<Product> productSet;
    private static Cart cart;

    @BeforeAll
    private static void setup(){
        Map<String, PromoStrategy> promoStrategyMap = new HashMap<>();
        promoStrategyMap.put(PromotionType.DIFFERENT_ITEMS_FIXED_PRICE.name(), new FixedPriceForDifferentItems());
        checkoutService = new CheckoutService(promoStrategyMap);
        checkoutService.cartRepository = cartRepository;

        promotion = createPromotion(PromotionType.DIFFERENT_ITEMS_FIXED_PRICE, "C + D = 30",
                BigDecimal.valueOf(30), "PROMO1", 0);

        product1 = createProduct("product1", BigDecimal.valueOf(20),"C", promotion);
        product2 = createProduct("product2", BigDecimal.valueOf(15),"D", promotion);

        productSet = new HashSet<>();
        productSet.add(product1);
        productSet.add(product2);

        CheckoutServiceTest.promotion.setProducts(productSet);

        CartItem item1 = createCartItem(1, product1);
        CartItem item2 = createCartItem(1, product2);

        cart = createCart(item1, item2);

    }

    private static Cart createCart(CartItem item1, CartItem item2) {
        Cart cart = new Cart();
        cart.setReference("testRef");
        cart.setUsername("test user");
        cart.setCartItems(new HashSet<>());
        Set<CartItem> itemSet = new HashSet<>();
        itemSet.add(item1);
        itemSet.add(item2);
        cart.setCartItems(itemSet);

        return cart;
    }

    private static CartItem createCartItem(int qty, Product product) {
        CartItem item = new CartItem();
        item.setCart(cart);
        item.setQuantity(qty);
        item.setProduct(product);
        return item;
    }

    private static Product createProduct(String name, BigDecimal price, String sku, Promotion promotion) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setSku(sku);
        product.setPromotion(promotion);

        return product;
    }

    private static Promotion createPromotion(PromotionType promotionType, String name, BigDecimal discount, String promoCode, Integer noOfItems) {
        Promotion promotion = new Promotion();
        promotion.setPromoType(promotionType);
        promotion.setName(name);
        promotion.setDiscount(discount);
        promotion.setPromoCode(promoCode);
        promotion.setNoOfItems(noOfItems);
        return promotion;
    }

    @Test
    public void testCheckout() throws Exception {
        setup();
        when(cartRepository.findByReference(any())).thenReturn(Optional.of(cart));
        CheckoutResponse response = checkoutService.checkout("testRef");

        assertNotNull(response);
        assertEquals(new BigDecimal(35), response.getTotalAmount());
        assertEquals(new BigDecimal(5), response.getTotalDiscount());
        assertEquals(new BigDecimal(30), response.getAmountDue());
    }
}
