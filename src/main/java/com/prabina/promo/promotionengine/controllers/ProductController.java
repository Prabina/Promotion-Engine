package com.prabina.promo.promotionengine.controllers;

import com.prabina.promo.promotionengine.dtos.GetProduct;
import com.prabina.promo.promotionengine.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<GetProduct>> getAllProducts(){
        List<GetProduct> products = productService.getAllProducts();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/products/{sku}")
    public ResponseEntity<GetProduct> getProductById(@PathVariable String sku) throws Exception {
        GetProduct product = productService.getProductBySku(sku);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
