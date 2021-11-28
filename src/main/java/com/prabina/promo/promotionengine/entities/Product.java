package com.prabina.promo.promotionengine.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor @Getter @Setter
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String sku;
    private String name;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;
}
