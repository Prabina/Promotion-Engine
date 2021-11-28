package com.prabina.promo.promotionengine.repositories;

import com.prabina.promo.promotionengine.entities.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion,Long> {
}
