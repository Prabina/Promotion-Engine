package com.prabina.promo.promotionengine.repositories;

import com.prabina.promo.promotionengine.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findBySku(String sku);
}
