package com.prabina.promo.promotionengine.repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.prabina.promo.promotionengine.entities.Cart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class CartRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    CartRepository cartRepository;

    @Test
    public void shouldFindACartByReference(){
        Cart cart = new Cart();
        cart.setReference("testRef");
        cart.setUsername("test user");
        cart = entityManager.persistAndFlush(cart);

        assertEquals(cart, cartRepository.findByReference("testRef").get());
    }
}
