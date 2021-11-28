package com.prabina.promo.promotionengine.services;

import static org.junit.jupiter.api.Assertions.*;

import com.prabina.promo.promotionengine.dtos.CheckoutResponse;
import com.prabina.promo.promotionengine.entities.Cart;
import com.prabina.promo.promotionengine.entities.CartItem;
import com.prabina.promo.promotionengine.entities.Product;
import com.prabina.promo.promotionengine.entities.Promotion;
import com.prabina.promo.promotionengine.promoEngine.FixedPriceForDifferentItems;
import com.prabina.promo.promotionengine.promoEngine.FixedPriceForNItems;
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

    private static Promotion promotion1;
    private static Promotion promotion2;
    private static Promotion promotion3;
    private static Product productA;
    private static Product productB;
    private static Product productC;
    private static Product productD;
    private static Cart cart;

    @BeforeAll
    private static void setup(){
        Map<String, PromoStrategy> promoStrategyMap = new HashMap<>();
        promoStrategyMap.put(PromotionType.DIFFERENT_ITEMS_FIXED_PRICE.name(), new FixedPriceForDifferentItems());
        promoStrategyMap.put(PromotionType.SAME_ITEM_FIXED_PRICE.name(), new FixedPriceForNItems());
        checkoutService = new CheckoutService(promoStrategyMap);
        checkoutService.cartRepository = cartRepository;

        promotion1 = createPromotion(PromotionType.DIFFERENT_ITEMS_FIXED_PRICE, "C + D = 30",
                BigDecimal.valueOf(30), "PROMO1", 0);
        promotion2 = createPromotion(PromotionType.SAME_ITEM_FIXED_PRICE, "3A's for 130",
                BigDecimal.valueOf(130),"PROMO2", 3);
        promotion3 = createPromotion(PromotionType.SAME_ITEM_FIXED_PRICE, "2B's for 45",
                BigDecimal.valueOf(45),"PROMO3", 2);

        productA = createProduct("productA", BigDecimal.valueOf(50),"A", promotion2);
        productB = createProduct("productB", BigDecimal.valueOf(30),"B", promotion3);
        productC = createProduct("productC", BigDecimal.valueOf(20),"C", promotion1);
        productD = createProduct("productD", BigDecimal.valueOf(15),"D", promotion1);

        Set<Product> productSet1 = new HashSet<>();
        productSet1.add(productC);
        productSet1.add(productD);
        promotion1.setProducts(productSet1);

        Set<Product> productSet2 = new HashSet<>();
        productSet2.add(productA);
        promotion2.setProducts(productSet2);

        Set<Product> productSet3 = new HashSet<>();
        productSet3.add(productB);
        promotion3.setProducts(productSet3);

        CartItem itemA = createCartItem(3, productA);
        CartItem itemB = createCartItem(5, productB);
        CartItem itemC = createCartItem(1, productC);
        CartItem itemD = createCartItem(1, productD);

        cart = createCart(List.of(itemA, itemB, itemC, itemD));

    }

    private static Cart createCart(List<CartItem> items) {
        Cart cart = new Cart();
        cart.setReference("testRef");
        cart.setUsername("test user");
        cart.setCartItems(new HashSet<>());
        Set<CartItem> itemSet = new HashSet<>();
        for(CartItem item : items) {
            itemSet.add(item);
        }
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
        assertEquals(new BigDecimal(335), response.getTotalAmount());
        assertEquals(new BigDecimal(55), response.getTotalDiscount());
        assertEquals(new BigDecimal(280), response.getAmountDue());
    }
}
