package com.prabina.promo.promotionengine.repositories;

import com.prabina.promo.promotionengine.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findBySku(String sku);
}
