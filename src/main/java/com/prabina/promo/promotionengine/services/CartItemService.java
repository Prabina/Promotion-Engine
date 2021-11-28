package com.prabina.promo.promotionengine.services;

import com.prabina.promo.promotionengine.dtos.AddToCartRequest;
import com.prabina.promo.promotionengine.dtos.AddToCartResponse;
import com.prabina.promo.promotionengine.entities.Cart;
import com.prabina.promo.promotionengine.entities.CartItem;
import com.prabina.promo.promotionengine.entities.Product;
import com.prabina.promo.promotionengine.exceptions.CartNotFoundException;
import com.prabina.promo.promotionengine.exceptions.ExceptionMessages;
import com.prabina.promo.promotionengine.exceptions.ProductNotFoundException;
import com.prabina.promo.promotionengine.repositories.CartItemRepository;
import com.prabina.promo.promotionengine.repositories.CartRepository;
import com.prabina.promo.promotionengine.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    public AddToCartResponse addToCart(String reference, AddToCartRequest request) throws CartNotFoundException, ProductNotFoundException {
        Optional<Cart> cartOptional = cartRepository.findByReference(reference);
        if (cartOptional.isEmpty()) {
            throw new CartNotFoundException(ExceptionMessages.CART_NOT_FOUND);
        }
        Optional<Product> productOptional = productRepository.findBySku(request.getSku());

        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException(ExceptionMessages.PRODUCT_NOT_FOUND);
        }
        Cart cart = cartOptional.get();
        Optional<CartItem> cartItemOptional = getItemFromCart(cart, productOptional.get());
        CartItem cartItem;

        if(cartItemOptional.isEmpty()){
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(productOptional.get());
            cartItem.setQuantity(request.getQty());
        } else {
            cartItem = cartItemOptional.get();
            cartItem.incrementQuantity(request.getQty());
        }

        saveCartItem(cart, cartItem);

        ModelMapper mapper = new ModelMapper();
        AddToCartResponse response = mapper.map(cartItem, AddToCartResponse.class);

        return response;

    }

    private void saveCartItem(Cart cart, CartItem cartItem) {
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    private Optional<CartItem> getItemFromCart(Cart cart, Product product) {
        Product existingProduct;
        for (CartItem item: cart.getCartItems()) {
            existingProduct = item.getProduct();
            if (existingProduct.getId().equals(product.getId())) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

}
