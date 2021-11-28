package com.prabina.promo.promotionengine.controllers;

import com.prabina.promo.promotionengine.dtos.AddToCartRequest;
import com.prabina.promo.promotionengine.dtos.AddToCartResponse;
import com.prabina.promo.promotionengine.exceptions.CartNotFoundException;
import com.prabina.promo.promotionengine.exceptions.ProductNotFoundException;
import com.prabina.promo.promotionengine.services.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartItemController {

    @Autowired
    CartItemService cartItemService;

    @PostMapping("/{reference}/items")
    public ResponseEntity<AddToCartResponse> addCartItem(@PathVariable String reference, @RequestBody AddToCartRequest request) throws CartNotFoundException, ProductNotFoundException {

        AddToCartResponse response = cartItemService.addToCart(reference, request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
