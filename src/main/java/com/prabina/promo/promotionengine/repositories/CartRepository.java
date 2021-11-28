package com.prabina.promo.promotionengine.repositories;

import com.prabina.promo.promotionengine.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByReference(String cartReference);
}
