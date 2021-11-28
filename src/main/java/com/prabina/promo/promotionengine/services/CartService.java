package com.prabina.promo.promotionengine.services;

import com.prabina.promo.promotionengine.dtos.CreateCartRequest;
import com.prabina.promo.promotionengine.dtos.CreateCartResponse;
import com.prabina.promo.promotionengine.dtos.GetCartByReference;
import com.prabina.promo.promotionengine.entities.Cart;
import com.prabina.promo.promotionengine.exceptions.CartNotFoundException;
import com.prabina.promo.promotionengine.exceptions.ExceptionMessages;
import com.prabina.promo.promotionengine.repositories.CartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    public CreateCartResponse createCart(CreateCartRequest request) {

        Cart cart = new Cart();
        cart.setUsername(request.getUserName());
        String reference = UUID.randomUUID().toString();
        cart.setReference(reference);
        cartRepository.save(cart);

        ModelMapper mapper = new ModelMapper();
        CreateCartResponse response = mapper.map(cart, CreateCartResponse.class);

        return response;
    }

    public GetCartByReference getCartByReference(String reference) throws CartNotFoundException {
        Optional<Cart> cartOptional = cartRepository.findByReference(reference);
        GetCartByReference response = null;

        if (cartOptional.isEmpty()) {
            throw new CartNotFoundException(ExceptionMessages.CART_NOT_FOUND +" for "+ reference);
        } else {
            Cart cart = cartOptional.get();
            ModelMapper mapper = new ModelMapper();
            response = mapper.map(cart, GetCartByReference.class);
        }

        return response;
    }
}
