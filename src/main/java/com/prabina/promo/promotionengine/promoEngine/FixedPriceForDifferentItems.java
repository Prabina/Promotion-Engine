package com.prabina.promo.promotionengine.promoEngine;

import com.prabina.promo.promotionengine.entities.Cart;
import com.prabina.promo.promotionengine.entities.CartItem;
import com.prabina.promo.promotionengine.entities.Product;
import com.prabina.promo.promotionengine.entities.Promotion;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class FixedPriceForDifferentItems implements PromoStrategy{

    @Override
    public BigDecimal applyPromo(CartItem cartItem) {

        BigDecimal promoDiscount = BigDecimal.ZERO;

        if(!cartItem.isPromoApplied()){

            Promotion promotion = cartItem.getProduct().getPromotion();
            Set<Product> promoProducts = promotion.getProducts();
            Cart cart = cartItem.getCart();

            Map<String, CartItem> productCartItemMap = cart.getCartItems().stream().
                    collect(Collectors.toMap(item -> item.getProductSku(), item -> item));

            List<CartItem> promoCartItems = getPromoCartItems(promoProducts, productCartItemMap);


            if (!promoCartItems.isEmpty()) {
                BigDecimal noOfPromosApplied = getMaxNoOfPromosApplied(promoCartItems);

                BigDecimal pricePerBundleBeforeDiscount = getPriceBeforeDiscount(promoCartItems);

                BigDecimal pricePerBundleAfterDiscount = pricePerBundleBeforeDiscount.subtract(promotion.getDiscount());

                promoDiscount = pricePerBundleAfterDiscount.multiply(noOfPromosApplied);

            }

        }
        return promoDiscount;
    }

    private List<CartItem> getPromoCartItems(Set<Product> promoProducts, Map<String, CartItem> productCartItemMap) {
        List<CartItem> promoCartItems = new ArrayList<>();

        for (Product product: promoProducts) {
            if (productCartItemMap.containsKey(product.getSku())) {
                CartItem promoItem = productCartItemMap.get(product.getSku());
                if (promoItem.isPromoApplied()) {
                    promoCartItems.clear();
                    break;
                } else {
                    promoCartItems.add(productCartItemMap.get(product.getSku()));
                }
            } else {
                promoCartItems.clear();
                break;
            }
        }
        return promoCartItems;
    }

    private BigDecimal getMaxNoOfPromosApplied(List<CartItem> promoCartItems){
        Optional<CartItem> cartItemWithMinQty = promoCartItems.stream().min(Comparator.comparing(CartItem::getQuantity));
        Integer minQty = cartItemWithMinQty.get().getQuantity();
        BigDecimal noOfPromosApplied = new BigDecimal(minQty);

        return noOfPromosApplied;
    }

    private BigDecimal getPriceBeforeDiscount(List<CartItem> promoCartItems) {
        BigDecimal beforeDiscount = BigDecimal.ZERO;

        for (CartItem promoCartItem : promoCartItems) {
            promoCartItem.setPromoApplied(true);

            beforeDiscount = beforeDiscount.add(promoCartItem.getProduct().getPrice());
        }
        return beforeDiscount;
    }
}
