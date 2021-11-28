package com.prabina.promo.promotionengine.repositories;

import com.prabina.promo.promotionengine.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByReference(String cartReference);
}
