package com.prabina.promo.promotionengine.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "promo_code")
    private String promoCode;
    @Column(name = "promo_type")
    private String promoType;
    private Integer discount;
    @Column(name = "no_of_items")
    private Integer noOfItems;

    @OneToMany(mappedBy = "promotion", fetch = FetchType.EAGER)
    private Set<Product> products;
}
