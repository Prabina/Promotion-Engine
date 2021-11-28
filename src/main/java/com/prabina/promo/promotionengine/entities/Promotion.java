package com.prabina.promo.promotionengine.entities;


import com.prabina.promo.promotionengine.promoEngine.PromotionType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor
@ToString
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "promo_code")
    private String promoCode;

    @Column(name = "promo_type")
    @Enumerated(EnumType.STRING)
    private PromotionType promoType;

    private BigDecimal discount;

    @Column(name = "no_of_items")
    private Integer noOfItems;

    @OneToMany(mappedBy = "promotion", fetch = FetchType.EAGER)
    private Set<Product> products;
}
