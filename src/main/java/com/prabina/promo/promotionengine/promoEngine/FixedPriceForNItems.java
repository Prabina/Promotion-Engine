package com.prabina.promo.promotionengine.promoEngine;

import com.prabina.promo.promotionengine.entities.CartItem;

import java.math.BigDecimal;

public class FixedPriceForNItems implements PromoStrategy{
    @Override
    public BigDecimal applyPromo(CartItem cartItem) {
        return null;
    }
}
