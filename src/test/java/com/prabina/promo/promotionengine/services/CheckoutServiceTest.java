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
    private static Promotion alreadyAppliedPromotion;
    private static Product product1;
    private static Product product2;
    private static Product product3;
    private static Set<Product> productSet1;
    private static Set<Product> productSet2;
    private static Cart cart = new Cart();

    @BeforeAll
    private static void setup(){
        Map<String, PromoStrategy> promoStrategyMap = new HashMap<>();
        promoStrategyMap.put(PromotionType.DIFFERENT_ITEMS_FIXED_PRICE.name(), new FixedPriceForDifferentItems());
        checkoutService = new CheckoutService(promoStrategyMap);
        checkoutService.cartRepository = cartRepository;

        cart.setReference("testRef");
        cart.setUsername("test user");
        cart.setCartItems(new HashSet<>());

        promotion = new Promotion();
        promotion.setPromoType(PromotionType.DIFFERENT_ITEMS_FIXED_PRICE);
        promotion.setDiscount(BigDecimal.valueOf(5));
        promotion.setPromoCode("PROMO1");

        alreadyAppliedPromotion = new Promotion();
        alreadyAppliedPromotion.setPromoType(PromotionType.SAME_ITEM_FIXED_PRICE);
        alreadyAppliedPromotion.setDiscount(BigDecimal.valueOf(5));
        alreadyAppliedPromotion.setPromoCode("PROMO2");

        product1 = new Product();
        product1.setName("product1");
        product1.setPrice(new BigDecimal(10));
        product1.setSku("SKU1");
        product1.setPromotion(promotion);

        product2 = new Product();
        product2.setName("product2");
        product2.setPrice(new BigDecimal(10));
        product2.setSku("SKU2");
        product2.setPromotion(promotion);

        product3 = new Product();
        product3.setName("product3");
        product3.setPrice(new BigDecimal(5));
        product3.setSku("SKU3");
        product3.setPromotion(alreadyAppliedPromotion);

        productSet1 = new HashSet<>();
        productSet1.add(product1);
        productSet1.add(product2);

        productSet2 = new HashSet<>();
        productSet2.add(product3);

        promotion.setProducts(productSet1);
        alreadyAppliedPromotion.setProducts(productSet2);

        CartItem item1 = new CartItem();
        item1.setCart(cart);
        item1.setQuantity(1);
        item1.setProduct(product1);

        CartItem item2 = new CartItem();
        item2.setCart(cart);
        item2.setQuantity(2);
        item2.setProduct(product2);

        CartItem item3 = new CartItem();
        item3.setCart(cart);
        item3.setQuantity(2);
        item3.setProduct(product3);
        item3.setPromoApplied(true);

        Set<CartItem> itemSet = new HashSet<>();
        itemSet.add(item1);
        itemSet.add(item2);
        cart.setCartItems(itemSet);

    }

    @Test
    public void testCheckout() throws Exception {
        setup();
        when(cartRepository.findByReference(any())).thenReturn(Optional.of(cart));
        CheckoutResponse response = checkoutService.checkout("testRef");

        assertNotNull(response);
        assertEquals(new BigDecimal(30), response.getTotalAmount());
        assertEquals(new BigDecimal(15), response.getTotalDiscount());
        assertEquals(new BigDecimal(15), response.getAmountDue());
    }
}
