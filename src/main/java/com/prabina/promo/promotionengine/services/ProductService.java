package com.prabina.promo.promotionengine.services;

import com.prabina.promo.promotionengine.dtos.GetProduct;
import com.prabina.promo.promotionengine.entities.Product;
import com.prabina.promo.promotionengine.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<GetProduct> getAllProducts() {
        List<GetProduct> response = new ArrayList<>();

        List<Product> products = productRepository.findAll();

        ModelMapper mapper = new ModelMapper();
        for (Product product : products){
            GetProduct dto = mapper.map(product,GetProduct.class);
            response.add(dto);
        }

        return response;
    }

}
