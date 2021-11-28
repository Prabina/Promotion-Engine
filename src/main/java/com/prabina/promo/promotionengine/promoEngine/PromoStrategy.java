package com.prabina.promo.promotionengine.promoEngine;

import com.prabina.promo.promotionengine.entities.CartItem;

import java.math.BigDecimal;

public interface PromoStrategy {
    BigDecimal applyPromo(CartItem cartItem);
}
