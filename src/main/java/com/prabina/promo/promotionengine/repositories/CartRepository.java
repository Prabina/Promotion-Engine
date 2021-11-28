package com.prabina.promo.promotionengine.repositories;

import com.prabina.promo.promotionengine.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
