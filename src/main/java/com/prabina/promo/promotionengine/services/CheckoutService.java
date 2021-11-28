package com.prabina.promo.promotionengine.services;

import com.prabina.promo.promotionengine.dtos.CheckoutResponse;
import com.prabina.promo.promotionengine.entities.Cart;
import com.prabina.promo.promotionengine.entities.CartItem;
import com.prabina.promo.promotionengine.exceptions.ExceptionMessages;
import com.prabina.promo.promotionengine.promoEngine.PromoStrategy;
import com.prabina.promo.promotionengine.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
public class CheckoutService {

    @Autowired
    CartRepository cartRepository;

    private final Map<String, PromoStrategy> promoTypePromoStrategyMap;


    public CheckoutService(Map<String, PromoStrategy> promoStrategyMap) {
        this.promoTypePromoStrategyMap = promoStrategyMap;
    }

    public CheckoutResponse checkout(String reference) throws ClassNotFoundException {
        Optional<Cart> cartOptional = cartRepository.findByReference(reference);

        if (cartOptional.isEmpty()) {
            throw new ClassNotFoundException(ExceptionMessages.CART_NOT_FOUND);
        }

        Cart cart = cartOptional.get();
        BigDecimal totalAmount = new BigDecimal(0);
        BigDecimal totalDiscount = new BigDecimal(0);

        for (CartItem cartItem: cart.getCartItems()) {
            totalAmount = totalAmount.add(cartItem.getTotalPrice());
            totalDiscount = totalDiscount.add(getCartItemDiscount(cartItem));
        }


        return new CheckoutResponse(cart.getUsername(), totalAmount, totalDiscount, totalAmount.subtract(totalDiscount));
    }

    private BigDecimal getCartItemDiscount(CartItem cartItem) {
        BigDecimal discount = BigDecimal.ZERO;

        if (cartItem.getProduct().getPromotion() != null) {

            String promoType = cartItem.getProduct().getPromotion().getPromoType().name();
            PromoStrategy promoStrategy = promoTypePromoStrategyMap.get(promoType);
            discount = promoStrategy.applyPromo(cartItem);
        }

        return discount;
    }
}
