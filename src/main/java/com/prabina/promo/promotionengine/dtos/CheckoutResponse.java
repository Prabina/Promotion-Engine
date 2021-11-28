package com.prabina.promo.promotionengine.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutResponse {
    private String username;
    private BigDecimal totalAmount;
    private BigDecimal totalDiscount;
    private BigDecimal amountDue;
}
