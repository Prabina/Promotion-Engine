package com.prabina.promo.promotionengine.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddToCartRequest {
    public String sku;
    public Integer qty;
}
