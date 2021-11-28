package com.prabina.promo.promotionengine.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class GetCartItem {
    private String name;
    private String sku;
    private BigDecimal price;
    private Integer qty;
}
