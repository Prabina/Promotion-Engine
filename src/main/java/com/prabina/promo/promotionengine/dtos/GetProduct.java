package com.prabina.promo.promotionengine.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class GetProduct {
    private  String sku;
    private  String name;
    private  BigDecimal price;
}
