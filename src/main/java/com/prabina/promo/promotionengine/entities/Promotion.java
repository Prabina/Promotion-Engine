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
    @GeneratedValue
    private Long id;
    private String name;
    private String promoCode;
    private String promoType;
    private Integer discount;
    private Integer noOfItems;

    @OneToMany(mappedBy = "promotion", fetch = FetchType.EAGER)
    private Set<Product> products;
}
