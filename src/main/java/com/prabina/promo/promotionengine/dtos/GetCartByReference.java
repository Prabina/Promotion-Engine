package com.prabina.promo.promotionengine.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class GetCartByReference {
    private String username;
    private String reference;

    private Set<GetCartItem> cartItems;
}
