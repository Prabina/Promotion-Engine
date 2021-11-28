package com.prabina.promo.promotionengine.controllers;

import com.prabina.promo.promotionengine.dtos.CheckoutResponse;
import com.prabina.promo.promotionengine.dtos.CreateCartRequest;
import com.prabina.promo.promotionengine.dtos.CreateCartResponse;
import com.prabina.promo.promotionengine.dtos.GetCartByReference;
import com.prabina.promo.promotionengine.exceptions.CartNotFoundException;
import com.prabina.promo.promotionengine.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("/carts/{reference}")
    public ResponseEntity<GetCartByReference> getCartByReference(@PathVariable String reference) throws CartNotFoundException {
        GetCartByReference response = cartService.getCartByReference(reference);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/carts")
    public ResponseEntity<CreateCartResponse> createCart(@RequestBody CreateCartRequest request) {

        CreateCartResponse response = cartService.createCart(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
