package com.prabina.promo.promotionengine.promoEngine;

import com.prabina.promo.promotionengine.entities.CartItem;
import com.prabina.promo.promotionengine.entities.Promotion;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FixedPriceForNItems implements PromoStrategy{
    @Override
    public BigDecimal applyPromo(CartItem cartItem) {
        BigDecimal promoDiscount = BigDecimal.ZERO;

        if (!cartItem.isPromoApplied()) {
            Promotion promotion = cartItem.getProduct().getPromotion();

            if (promotion.getNoOfItems() <= cartItem.getQuantity()) {

                int noOfPromosApplied = cartItem.getQuantity() / promotion.getNoOfItems();

                BigDecimal price = cartItem.getProduct().getPrice();
                BigDecimal qty = new BigDecimal(noOfPromosApplied * promotion.getNoOfItems());

                BigDecimal beforeDiscount = price.multiply(qty);

                int discountedAmount = cartItem.getQuantity() / promotion.getNoOfItems() * promotion.getDiscount().intValue();

                promoDiscount = beforeDiscount.subtract(new BigDecimal(discountedAmount));

                cartItem.setPromoApplied(true);
            }
        }
        return promoDiscount;
    }
}
