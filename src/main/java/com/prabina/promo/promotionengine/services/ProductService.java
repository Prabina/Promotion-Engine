package com.prabina.promo.promotionengine.services;

import com.prabina.promo.promotionengine.dtos.GetProduct;
import com.prabina.promo.promotionengine.entities.Product;
import com.prabina.promo.promotionengine.exceptions.ExceptionMessages;
import com.prabina.promo.promotionengine.exceptions.ProductNotFoundException;
import com.prabina.promo.promotionengine.repositories.ProductRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public GetProduct getProductBySku(String sku) throws Exception {

        Optional<Product> productOptional = productRepository.findBySku(sku);
        if(productOptional.isEmpty()){
            throw new ProductNotFoundException(ExceptionMessages.PRODUCT_NOT_FOUND);
        }

        ModelMapper mapper = new ModelMapper();
        GetProduct response = mapper.map(productOptional.get(), GetProduct.class);

        return response;
    }
}
